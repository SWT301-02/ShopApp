package sample.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.user.UserGoogleDTO;
import sample.utils.Constants;

@WebServlet(name = "CallbackController", urlPatterns = { "/CallbackController" })
public class CallbackController extends HttpServlet {

    private static final String AD = "AD";
    private static final String US = "US";
    private static final String AD_PAGE = "admin.jsp";
    private static final String US_PAGE = "user.jsp";
    private static final String REGISTER = "register.jsp";
    private static final String ERROR = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ClassNotFoundException, SQLException, ServletException, NamingException {
        String url = ERROR;
        try {
            String code = request.getParameter("code");
            System.out.println("code: " + code);
            String accessToken = getToken(code);
            System.out.println("accessToken: " + accessToken);
            UserGoogleDTO userGoogle = getUserInfo(accessToken);
            System.out.println(userGoogle.toString());

            if (userGoogle != null) {
                UserDAO dao = new UserDAO();
                UserDTO userDTO = dao.checkUserByEmail(userGoogle.getEmail());
                if (userDTO != null) {
                    switch (userDTO.getRoleID()) {
                        case AD:
                            url = AD_PAGE;
                            break;
                        case US:
                            url = US_PAGE;
                            break;
                        default:
                            request.setAttribute("ERROR", "Your role is not support yet!");
                            url = ERROR;
                            break;
                    }
                    request.getSession().setAttribute("LOGIN_USER", userDTO);
                } else {
                    request.setAttribute("email", userGoogle.getEmail());
                    request.setAttribute("fullName", userGoogle.getName());
                    url = REGISTER;
                }
            } else {
                request.setAttribute("ERROR", "email does not exist!");
                url = ERROR;
            }

        } catch (IOException | ClassNotFoundException | SQLException e) {
            log("Error at CallbackController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        try {
            String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                    .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                            .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                            .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI)
                            .add("grant_type", Constants.GOOGLE_GRANT_TYPE)
                            .add("code", code).build())
                    .execute().returnContent().asString();

            // Log the full response for debugging
            System.out.println("Token response: " + response);

            JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
            String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
            return accessToken;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static UserGoogleDTO getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDTO googlePojo = new Gson().fromJson(response, UserGoogleDTO.class);
        return googlePojo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NamingException ex) {
            Logger.getLogger(CallbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException | NamingException ex) {
            Logger.getLogger(CallbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
