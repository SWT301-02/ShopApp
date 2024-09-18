/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.user.UserError;

/**
 *
 * @author lmao
 */
@WebServlet(name = "InsertController", urlPatterns = {"/InsertController"})
public class InsertController extends HttpServlet {

    private static final String ERROR_AD = "insert.jsp";
    private static final String SUCCESS_AD = "insert.jsp";

    private static final String ERROR_REGIS = "register.jsp";
    private static final String SUCCESS_REGIS = "register.jsp";

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
        String url = ERROR_AD;
        boolean check = true;
        UserDAO dao = new UserDAO();
        UserError userError = new UserError();
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("LOGIN_USER");
        if (loginUser == null) {
            loginUser = new UserDTO("", "", "", "US","");
        }

        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            String email = request.getParameter("email");

            // validation basic
            if (userID.length() < 2 || userID.length() > 10) {
                check = false;
                userError.setUserIDError("UserID must be in range [2,10]");
            }
            if (fullName.length() < 5 || fullName.length() > 25) {
                check = false;
                userError.setFullNameError("fullName must be in range [5,25]");
            }
            if (password.length() < 5 || password.length() > 20) {
                check = false;
                userError.setConfirmError("Password must be in range [5,20]");
            }
            if (!password.equals(confirm)) {
                check = false;
                userError.setConfirmError("Password is not matched!");
            }
            if (dao.checkDublicate(userID)) {
                check = false;
                userError.setUserIDError("UserID already registed!");
            }
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                check = false;
                userError.setEmailError("Email must be true format!");
            }
            if (dao.checkDublicateEmail(email)) {
                check = false;
                userError.setEmailError("Email already registed!");
            }
            if (check) {
                String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                UserDTO user = new UserDTO(userID, fullName, roleID, hashedPassword, email);
                boolean insert = dao.insertv2(user);
                if (insert) {
                    request.setAttribute("SUCCESS", "User Created!");
                    if ("AD".equals(loginUser.getRoleID())) {
                        url = SUCCESS_AD;
                    } else {
                        url = SUCCESS_REGIS;
                    }

                } else {
                    userError.setError("Unknown Error!");
                    request.setAttribute("USER_ERROR", userError);
                    if ("AD".equals(loginUser.getRoleID())) {
                        url = ERROR_AD;
                    } else {
                        url = ERROR_REGIS;
                    }
                }
            } else {
                request.setAttribute("USER_ERROR", userError);
                if ("AD".equals(loginUser.getRoleID())) {
                    url = ERROR_AD;
                } else {
                    url = ERROR_REGIS;
                }
            }

        } catch (Exception e) {
            log("Error at InsertController: " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                userError.setUserIDError("UserID duplicated!");
                request.setAttribute("USER_ERROR", userError);
            }
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String email = "manhduonglhp4@gmail.c";
        boolean check = true;
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            check = false;
        }
        System.out.println("Email format: " + check);
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
