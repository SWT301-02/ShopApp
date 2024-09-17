<%-- 
    Document   : thankyou
    Created on : Aug 21, 2024, 9:20:37 AM
    Author     : lmao
--%>

<%@page import="sample.shopping.CartDTO" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thank You</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- Optional custom styles -->
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link href="css/image.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <!-- Navigation Bar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">Shop</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ml-auto">
                        <c:if test="${sessionScope.LOGIN_USER != null}">
                            <li class="nav-item">
                                <a class="nav-link" href="#">Welcome, ${sessionScope.LOGIN_USER.fullName}</a>
                            </li>
                            <li class="nav-item">
                                <form action="MainController" method="POST" class="d-inline">
                                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Log Out</button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form action="MainController" method="POST" class="d-inline">
                                    <button type="submit" name="action" value="ViewShop" class="btn btn-primary">Back to Shop</button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-info nav-link" href="user.jsp">User Page</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.LOGIN_USER == null}">
                            <li class="nav-item">
                                <a class="btn btn-success nav-link" href="login.jsp">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-primary nav-link" href="register.jsp">Register</a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>

        <!-- Thank You Message -->
        <div class="container mt-4">
            <div class="alert alert-success" role="alert">
                <h4 class="alert-heading">Thank You for Your Payment!</h4>
                <p>${requestScope.SUCCESS}</p>
                <c:if test="${requestScope.ERROR != null}">
                    <div class="alert alert-danger" role="alert">
                        <p>${requestScope.ERROR}</p>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
