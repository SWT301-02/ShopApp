/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.shopping.CartDTO;
import sample.shopping.ProductDAO;
import sample.shopping.ProductDTO;

/**
 *
 * @author lmao
 */
@WebServlet(name = "ChangeController", urlPatterns = {"/ChangeController"})
public class ChangeController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS = "cart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            ProductDAO productDAO = new ProductDAO();
            String id = request.getParameter("productID");
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            boolean checkQuan = productDAO.checkQuantity(id, newQuantity);
            if (checkQuan) {
                if (cart != null) {
                    if (cart.getCart().containsKey(id)) {
                        String name = cart.getCart().get(id).getProductName();
                        float price = cart.getCart().get(id).getPrice();
                        String imageUrl = cart.getCart().get(id).getImageUrl();
                        ProductDTO product = new ProductDTO(id, name, price, newQuantity, imageUrl);
                        boolean check = cart.change(id, product);
                        if (check) {
                            session.setAttribute("CART", cart);
                            request.setAttribute("SUCCESS", "Change " + name + ": " + newQuantity);
                            url = SUCCESS;
                        }
                    }
                }
            } else {
                request.setAttribute("ERROR", "Selected out of stock. Max: " + productDAO.getQuantity(id));
            }

        } catch (Exception e) {
            log("Error at AddToCartController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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