package sample.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sample.user.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class AuthenFilterTest {

    private AuthenFilter authenFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenFilter = new AuthenFilter();
    }

    @Test
    @Order(1)
    void testDoFilter_StaticResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/styles/main.css");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    @Order(2)
    void testDoFilter_NonAuthenticatedResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/login.jsp");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_AuthenticatedResourceNoSession() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/user.jsp");
        when(request.getSession(false)).thenReturn(null);

        authenFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("MainController");
    }

    @Test
    void testDoFilter_AuthenticatedResourceValidUserSession() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/user.jsp");
        when(request.getSession(false)).thenReturn(session);

        UserDTO user = new UserDTO();
        user.setRoleID("US");

        session.setAttribute("LOGIN_USER", user);

        when(session.getAttribute("LOGIN_USER")).thenReturn(user);

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_AdminResourceValidAdminSession() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/admin.jsp");
        when(request.getSession(false)).thenReturn(session);

        UserDTO admin = new UserDTO();
        admin.setRoleID("AD");
        when(session.getAttribute("LOGIN_USER")).thenReturn(admin);

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_AdminResourceInvalidUserSession() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/admin.jsp");
        when(request.getSession(false)).thenReturn(session);

        UserDTO user = new UserDTO();
        user.setRoleID("US");
        when(session.getAttribute("LOGIN_USER")).thenReturn(user);

        authenFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoFilter_StaticResourceJS() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/scripts/main.js");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_StaticResourcePNG() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/images/logo.png");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_NonAuthenticatedResourceRegister() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/register.jsp");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_NonAuthenticatedResourceLoginGoogle() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/LoginGoogleController");

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_AuthenticatedResourceNoUser() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/user.jsp");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("LOGIN_USER")).thenReturn(null);

        authenFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("MainController");
    }

    @Test
    void testDoFilter_UserAccessingAdminResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/admin.jsp");
        when(request.getSession()).thenReturn(session);

        UserDTO user = new UserDTO();
        user.setRoleID("US");
        when(session.getAttribute("LOGIN_USER")).thenReturn(user);

        authenFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("login.jsp");
    }

    @Test
    void testDoFilter_AdminAccessingUserResource() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/user.jsp");
        when(request.getSession()).thenReturn(session);

        UserDTO admin = new UserDTO();
        admin.setRoleID("AD");
        when(session.getAttribute("LOGIN_USER")).thenReturn(admin);

        authenFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilter_InvalidRoleID() throws IOException, ServletException {
        when(request.getRequestURI()).thenReturn("/user.jsp");
        when(request.getSession()).thenReturn(session);

        UserDTO invalidUser = new UserDTO();
        invalidUser.setRoleID("INVALID");
        when(session.getAttribute("LOGIN_USER")).thenReturn(invalidUser);

        authenFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("login.jsp");
    }
}