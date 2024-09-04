<%-- 
    Document   : user
    Created on : Aug 7, 2024, 11:40:29 AM
    Author     : lmao
--%>

<%@page import="sample.user.UserDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <!-- Add Bootstrap CSS for styling -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <!-- User Details Section -->
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h1 class="h3 mb-0">Welcome, ${sessionScope.LOGIN_USER.fullName}!</h1>
                </div>
                <div class="card-body">
                    <p><strong>UserID:</strong> ${sessionScope.LOGIN_USER.userID}</p>
                    <p><strong>Full Name:</strong> ${sessionScope.LOGIN_USER.fullName}</p>
                    <p><strong>Role ID:</strong> ${sessionScope.LOGIN_USER.roleID}</p>
                    <a href="MainController?action=ViewShop" class="btn btn-success">Go to Shopping</a>
                </div>
            </div>

            <!-- Logout Button -->
            <div class="mt-3">
                <form action="MainController" method="POST">
                    <button type="submit" name="action" value="ViewCart" class="btn btn-warning">View Cart</button>
                    <button type="submit" name="action" value="Logout" class="btn btn-danger">Logout</button>
                </form>
            </div>
        </div>

        <!-- Add Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
