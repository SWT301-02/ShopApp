<%-- 
    Document   : topuser
    Created on : Aug 22, 2024, 1:44:07 PM
    Author     : lmao
--%>

<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/image.css">
    </head>
    <body>
        <%
            UserDTO user =(UserDTO) request.getAttribute("TOP_USER");
            if (user == null) {
                    user = new UserDTO();
                }
        %>
        <div class="container mt-4 content-container">
            <h1>Hello World!</h1>
        <table class="table table-striped align-middle mb-0 bg-white" border="1">
            <thead class="bg-light">
                <tr>
                    <th>userID</th>
                    <th>fullName</th>
                    <th>roleID</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><%= user.getUserID()%></td>
                    <td><%= user.getFullName()%></td>
                    <td><%= user.getRoleID()%></td>
                </tr>
            </tbody>
        </table>
        </div>
    </body>
</html>
