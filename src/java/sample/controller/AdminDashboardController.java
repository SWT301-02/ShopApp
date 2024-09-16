/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.shopping.CartDAO;
import sample.shopping.ProductDAO;
import sample.shopping.ProductSalesDTO;

/**
 *
 * @author lmao
 */
@WebServlet(name = "AdminDashboardController", urlPatterns = { "/AdminDashboardController" })
public class AdminDashboardController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            CartDAO cartDAO = new CartDAO();
            ProductDAO productDAO = new ProductDAO();

            // Fetch total sales
            double totalSales = cartDAO.getTotalSales();
            request.setAttribute("totalSales", String.format("%.2f", totalSales));

            // Fetch total orders
            int totalOrders = cartDAO.getTotalOrders();
            request.setAttribute("totalOrders", totalOrders);

            // Calculate average order value
            double averageOrderValue = totalOrders > 0 ? totalSales / totalOrders : 0;
            request.setAttribute("averageOrderValue", String.format("%.2f", averageOrderValue));

            // Fetch top selling products
            List<ProductSalesDTO> topSellingProducts = productDAO.getTopSellingProducts(5); // Get top 5 products
            request.setAttribute("topSellingProducts", topSellingProducts);

        } catch (SQLException e) {
            log("AdminDashboardController_SQL: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("admin.jsp").forward(request, response);
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
