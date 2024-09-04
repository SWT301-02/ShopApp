/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Hex;
import sample.shopping.CartDAO;
import sample.shopping.CartDTO;
import sample.shopping.ProductDAO;
import sample.shopping.ProductDTO;
import sample.user.UserDTO;
import sample.utils.Email;

/**
 *
 * @author lmao
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS_CASH = "thankyou.jsp";
    private static final String VNPAY = "PaymentController";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            int sum = 0;
            if (loginUser != null) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart != null && cart.cartSize() > 0) {
                    boolean isValidCart = true;
                    ProductDAO productDAO = new ProductDAO();

                    // Validate each product in the cart
                    for (ProductDTO product : cart.getCart().values()) {
                        // Example: Check if the product is available in stock
                        ProductDTO dbProduct = productDAO.getProductById(product.getProductID());
                        sum = +product.getQuantity();
                        if (dbProduct != null) {
                            if (dbProduct.getQuantity() < product.getQuantity()) {
                                isValidCart = false;
                                request.setAttribute("ERROR", "Product " + product.getProductName() + " is out of stock. Max: " + dbProduct.getQuantity());
                                url = ERROR;
                                break;
                            }
                        } else {
                            isValidCart = false;
                            request.setAttribute("ERROR", "Product " + product.getProductName() + " is stop selling.");
                            url = ERROR;
                            break;
                        }
                    }

                    if (isValidCart) {
                        // process payment method
                        String payMethod = request.getParameter("payMethod");
                        switch (payMethod) {
                            case "cash":
                                if (processPayment(cart, loginUser, "Tiền mặt")) {
                                    session.setAttribute("CART", null);
                                    url = SUCCESS_CASH;
                                } else {
                                    request.setAttribute("ERROR", "Process Failed!");
                                    url = ERROR;
                                }
                                break;
                            case "vnPay":
                                handleVnPayReturn(request, response);
                                break;
                            default:
                                request.setAttribute("ERROR", "Invalid Payment option!");
                                url = ERROR;
                                break;
                        }
                    }

                } else {
                    url = ERROR;
                    request.setAttribute("ERROR", "Select the product to checkout!");
                }
            } else {
                url = ERROR;
                request.setAttribute("ERROR", "You need to Login to proccess with the checkout!");
            }

        } catch (Exception e) {
            log("Error at CheckoutController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private boolean processPayment(CartDTO cart, UserDTO user, String paymentMethod) throws SQLException, ClassNotFoundException {
        try {
            CartDAO cartDAO = new CartDAO();
            double total = cart.getTotalPrice();
            int quantity = cart.getCartQuantity();
            int orderID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

            while (cartDAO.checkDuplicateID(orderID)) {
                orderID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
            }

            boolean check = cartDAO.saveOrder(orderID, user.getUserID(), total);

            if (check) {
                // Save each order detail and update product quantity
                for (ProductDTO product : cart.getCart().values()) {
                    boolean orderDetailSaved = cartDAO.saveOrderDetail(orderID, product);
                    if (!orderDetailSaved) {
                        log("Failed to save order details for product: " + product.getProductID());
                        return false;
                    }
                    boolean quantityUpdated = cartDAO.updateProductQuantity(product.getProductID(), product.getQuantity());
                    if (!quantityUpdated) {
                        log("Failed to update quantity for product: " + product.getProductID());
                        return false;
                    }
                }
                Email email = new Email();
                String mess = email.messageNewOrder(user.getFullName(), quantity, total, paymentMethod);
                email.sendEmail(email.subjectNewOrder(), mess, user.getEmail());
            } else {
                log("Fail to saveOrder!");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            log("Error at processPayment :" + e.getMessage());
        }

        return true;
    }

    private void handleVnPayReturn(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {
        String vnp_TmnCode = "XQ7Z0PZ0";
        String vnp_HashSecret = "51ZLEAD6W16PWFI3EDLM65PYOJPDEPO1";
        String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

        String amount = request.getParameter("amount");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_OrderInfo = "Thanh toan don hang";
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_IpAddr = request.getRemoteAddr();

        String vnp_TxnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        double amountInDollars = Double.parseDouble(amount);
        long amountInCents = (long) (amountInDollars * 100);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountInCents));
        vnp_Params.put("vnp_CurrCode", "USD");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/PRJ301_3w_1_JSLT/PaymentController");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_TxnTime);

        // Sort parameters by key
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        // Build hash data
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                if (hashData.length() > 0) {
                    hashData.append('&');
                    query.append('&');
                }
                hashData.append(fieldName).append('=').append(fieldValue);
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
            }
        }

        // Compute secure hash
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        // Build payment URL
        String paymentUrl = vnp_Url + "?" + query.toString();
        response.sendRedirect(paymentUrl);
    }

    private static String hmacSHA512(String key, String data) throws UnsupportedEncodingException {
        try {
            Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
            sha512_HMAC.init(secretKey);
            byte[] result = sha512_HMAC.doFinal(data.getBytes("UTF-8"));
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            throw new RuntimeException("Error while signing data", e);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(CheckoutController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(CheckoutController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
