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
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.utils.*;

/**
 *
 * @author lmao
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String AD = "AD";
    private static final String US = "US";
    private static final String AD_PAGE = "admin.jsp";
    private static final String US_PAGE = "user.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        // get reCaptcha 
        try {
//            String gRecaptcha = request.getParameter("g-recaptcha-response");
//            boolean isVerified = RecaptchaUtils.verify(gRecaptcha);
            boolean isVerified = true;
            if (isVerified) {
                HttpSession session = request.getSession();
                String userID = request.getParameter("userID");
                String password = request.getParameter("password");
                UserDAO dao = new UserDAO();

                UserDTO loginUser = dao.checkLoginv2(userID);
                PBKDF2 pbkdf2 = new PBKDF2();
                if (loginUser != null && pbkdf2.authenticate(password.toCharArray(), loginUser.getPassword())) {
                        switch (loginUser.getRoleID()) {
                            case AD:
                                url = AD_PAGE;
                                break;
                            case US:
                                url = US_PAGE;
                                break;
                            default:
                                request.setAttribute("ERROR", "Your role is not support yet!");
                                break;
                        }
                        loginUser.setPassword("***");
                        session.setAttribute("LOGIN_USER", loginUser);  
                } else {
                    request.setAttribute("ERROR", "userID or password incorrect!");
                    url = ERROR;
                }
            } else {
                request.setAttribute("ERROR", "Are you robot?");
                url = ERROR;
            }
        } catch (Exception e) {
            log("error at LoginController: " + e.getMessage());
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
