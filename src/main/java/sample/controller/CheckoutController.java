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
                                request.setAttribute("ERROR", "This Payment method not yet supported!");
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

    public boolean processPayment(CartDTO cart, UserDTO user, String paymentMethod) throws SQLException, ClassNotFoundException {
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