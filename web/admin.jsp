<%-- 
    Document   : cart
    Created on : Aug 14, 2024, 10:54:07 AM
    Author     : lmao
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <!-- Add Bootstrap CSS for styling -->
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/image.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-4">
            <h1>Welcome: ${sessionScope.LOGIN_USER.fullName}</h1>

            <form action="MainController" method="GET" class="form-inline mb-3">
                <div class="form-group">
                    <label for="search" class="sr-only">Search</label>
                    <input type="text" class="form-control mr-2" name="search" value="${param.search}">
                </div>
                <button type="submit" name="action" value="Search" class="btn btn-primary">Search</button>
            </form>

            <a href="MainController?action=Logout" class="btn btn-warning mb-3">Logout</a>
            <a href="insert.jsp" class="btn btn-success mb-3">Create</a>

            <c:if test="${requestScope.LIST_USER != null && not empty requestScope.LIST_USER}">
                <table class="table table-striped align-middle mb-0 bg-white">
                    <thead class="bg-light">
                        <tr>
                            <th>No.</th>
                            <th>UserID</th>
                            <th>Name</th>
                            <th>Role</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" varStatus="counter" items="${requestScope.LIST_USER}">
                        <form action="MainController" method="POST">
                            <tr>
                                <td>${counter.count}</td>
                                <td><p class="text-muted mb-0">${user.userID}</p></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <img src="https://mdbootstrap.com/img/new/avatars/${counter.count}.jpg" alt="" class="rounded-circle" style="width: 45px; height: 45px">
                                        <div class="ms-3">
                                            <input type="text" name="fullName" value="${user.fullName}" required=""/>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <select name="roleID" required>
                                        <option value="AD" ${user.roleID == 'AD' ? 'selected' : ''}>AD</option>
                                        <option value="US" ${user.roleID == 'US' ? 'selected' : ''}>US</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="hidden" name="userID" value="${user.userID}">
                                    <input type="hidden" name="search" value="${param.search}">
                                    <button type="submit" name="action" value="Update" class="btn btn-link btn-sm btn-rounded">Edit</button>
                                    <c:url var="deleteLink" value="MainController">
                                        <c:param name="action" value="Delete"/>
                                        <c:param name="userID" value="${user.userID}"/>
                                        <c:param name="search" value="${param.search}"/>
                                    </c:url>
                                    <a href="${deleteLink}" class="btn btn-link btn-sm btn-rounded">Delete</a>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <p style="color: red">${requestScope.ERROR}</p>
            <p style="color: green">${requestScope.SUCCESS}</p>
        </div>

        <!-- Add Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
