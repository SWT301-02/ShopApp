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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import sample.utils.Constants;

/**
 *
 * @author lmao
 */
@WebServlet(name = "LoginGoogleController", urlPatterns = { "/LoginGoogleController" })
public class LoginGoogleController extends HttpServlet {

    private static final String SCOPE = "email profile";
    private static final String AUTHORIZATION_ENDPOINT = "https://accounts.google.com/o/oauth2/auth";

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
        // Tạo URL ủy quyền Google
        String authorizationUrl = AUTHORIZATION_ENDPOINT + "?"
                + "response_type=code"
                + "&client_id=" + URLEncoder.encode(Constants.GOOGLE_CLIENT_ID, StandardCharsets.UTF_8.toString())
                + "&redirect_uri=" + URLEncoder.encode(Constants.GOOGLE_REDIRECT_URI, StandardCharsets.UTF_8.toString())
                + "&scope=" + URLEncoder.encode(SCOPE, StandardCharsets.UTF_8.toString())
                + "&access_type=offline";
        System.out.println("authorizationUrl:: " + authorizationUrl);
        response.sendRedirect(authorizationUrl);

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
            throws IOException, ServletException {
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
