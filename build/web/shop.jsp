<%-- 
    Document   : shop
    Created on : Aug 14, 2024, 11:07:31 AM
    Author     : lmao
--%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shop Page</title>
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/image.css">
    </head>
    <body>
        <!-- Navigation Bar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Shop</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
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
                                    <button type="submit" name="action" value="ViewCart" class="btn btn-primary nav-link">View Cart</button>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form action="MainController" method="POST" class="d-inline">
                                    <button type="submit" name="action" value="Logout" class="btn btn-warning nav-link">Log Out</button>
                                </form>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.LOGIN_USER == null}">
                            <li class="nav-item btn btn-outline-success nav-link mx-1">
                                <a class="nav-link" href="login.jsp">Login</a>
                            </li>
                            <li class="nav-item btn btn-outline-primary nav-link mx-1">
                                <a class="nav-link" href="register.jsp">Register</a>
                            </li>
                            <li class="nav-item btn btn-outline-warning nav-link mx-1">
                                <form action="MainController" method="POST" class="d-inline">
                                    <button class="nav-item btn btn-outline-warning nav-link" type="submit" name="action" value="ViewCart">View Cart</button>
                                </form>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container mt-4 content-container">
            <!-- Your page content here -->
            <h1 style="color: black">Welcome to ClothesShop!</h1>
            <form action="MainController" method="GET" class="form-inline mb-3">
                <div class="form-group">
                    <label for="search" class="sr-only">Search</label>
                    <input type="text" class="form-control mr-2" name="search" value="${param.search}">
                </div>
                <button type="submit" name="action" value="SearchProduct" class="btn btn-primary">Search</button>
            </form>

            <c:if test="${requestScope.LIST_PRODUCT != null && not empty requestScope.LIST_PRODUCT}">
                <p style="color: red">${requestScope.ERROR}</p>
                <p style="color: green">${requestScope.SUCCESS}</p>
                <table class="table table-striped align-middle mb-0 bg-white">
                    <thead class="bg-light">
                        <tr>
                            <th>Product ID</th>
                            <th>Image</th>
                            <th>Product Name</th>
                            <th>Select</th>
                            <th>In Stock</th>
                            <th>Price</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${requestScope.LIST_PRODUCT}">
                        <form action="MainController" method="POST">
                            <tr>
                                <td>${product.productID}</td>
                                <td>
                                    <img src="${product.imageUrl}" alt="${product.productName}" style="width: 200px; height: 200px" class="img-fluid rounded-3">
                                </td>
                                <td>${product.productName}</td>
                                <td>
                                    <input type="number" name="select" value="" min="1" max="${product.quantity}" class="form-control form-control-sm" required/>
                                    <input type="hidden" name="productID" value="${product.productID}" />
                                    <input type="hidden" name="productName" value="${product.productName}" />
                                    <input type="hidden" name="price" value="${product.price}" />
                                    <input type="hidden" name="imageUrl" value="${product.imageUrl}" />
                                    <input type="hidden" name="search" value="${param.search}"/>
                                </td>
                                <td>${product.quantity}</td>
                                <td>$${product.price}</td>
                                <td><button type="submit" name="action" value="Add" class="btn btn-link btn-sm">Add to Cart</button></td>
                            </tr>
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>

</html>