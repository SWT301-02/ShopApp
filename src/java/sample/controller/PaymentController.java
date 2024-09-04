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
import sample.shopping.ProductDTO;
import sample.user.UserDTO;
import sample.utils.Email;

/**
 *
 * @author lmao
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/PaymentController"})
public class PaymentController extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        String vnp_TmnCode = "XQ7Z0PZ0";
        String vnp_HashSecret = "51ZLEAD6W16PWFI3EDLM65PYOJPDEPO1";

        // Extract VNPay return parameters
        Map<String, String[]> parameters = request.getParameterMap();
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");

        // Recompute secure hash
        StringBuilder hashData = new StringBuilder();
        parameters.entrySet().stream()
                .filter(entry -> entry.getValue()[0] != null && !entry.getValue()[0].isEmpty() && !entry.getKey().equals("vnp_SecureHash"))
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> hashData.append(entry.getKey()).append('=').append(entry.getValue()[0]).append('&'));

        String computedHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        String url = ERROR;
        // Check if VNPay response is valid
        try {
            if (vnp_SecureHash.equals(computedHash) && "00".equals(vnp_ResponseCode)) {
                // Payment successful
                HttpSession session = request.getSession();
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
                if (processPayment(cart, user, "Thanh toán điện tử")) {
                    session.setAttribute("CART", null);
                    url = SUCCESS_CASH;
                } else {
                    request.setAttribute("ERROR", "Process Failed!");
                    url = ERROR;
                }
            } else {
                // Payment failed or invalid
                request.setAttribute("ERROR", "Payment failed or invalid response from VNPay.");
                url = ERROR;
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher("thankyou.jsp").forward(request, response);
        }
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
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
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
