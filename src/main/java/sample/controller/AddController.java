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
import sample.shopping.ProductDTO;

/**
 *
 * @author lmao
 */
@WebServlet(name = "AddController", urlPatterns = { "/AddController" })
public class AddController extends HttpServlet {

    private final static String ERROR = "SearchProductController";
    private final static String SUCCESS = "SearchProductController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");

            float price = Float.parseFloat(request.getParameter("price"));
            int select = Integer.parseInt(request.getParameter("select"));
            String imageUrl = request.getParameter("imageUrl");
            HttpSession session = request.getSession();
            if (session != null) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart == null) {
                    cart = new CartDTO();
                }
                ProductDTO product = new ProductDTO(productID, productName, price, select, imageUrl);
                boolean check = cart.add(productID, product);
                if (check) {
                    request.setAttribute("SUCCESS", "Added " + productName + ": " + select);
                    session.setAttribute("CART", cart);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Added Fail!");
                    url = ERROR;
                }
            }
        } catch (Exception e) {
            log("Error at AddController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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