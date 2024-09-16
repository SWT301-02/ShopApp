<%-- Document : cart Created on : Aug 14, 2024, 10:54:07 AM Author : lmao --%>

    <%@page import="sample.shopping.CartDTO" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Shopping Cart</title>
                    <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
                    <link
                        href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Montserrat:wght@300;400;600&display=swap"
                        rel="stylesheet">
                    <link rel="stylesheet"
                        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
                    <link rel="stylesheet" href="css/shop.css">
                    <style>
                        .cart-container {
                            max-width: 1200px;
                            margin: 0 auto;
                            padding: 50px 20px;
                        }

                        .cart-header {
                            text-align: center;
                            margin-bottom: 30px;
                        }

                        .cart-table {
                            width: 100%;
                            border-collapse: collapse;
                        }

                        .cart-table th,
                        .cart-table td {
                            padding: 15px;
                            text-align: left;
                            border-bottom: 1px solid #ddd;
                        }

                        .cart-table th {
                            background-color: #f8f5f1;
                            font-weight: bold;
                        }

                        .cart-total {
                            text-align: right;
                            margin-top: 20px;
                            font-size: 1.2em;
                        }

                        .btn-remove {
                            background-color: #d4af37;
                            color: #1c1c1c;
                            border: none;
                            padding: 5px 10px;
                            cursor: pointer;
                            transition: all 0.3s ease;
                        }

                        .btn-remove:hover {
                            background-color: #1c1c1c;
                            color: #d4af37;
                        }
                    </style>
                </head>

                <body><!-- Navigation Bar -->
                    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                        <div class="container">
                            <a class="navbar-brand" href="#">Luxury Fashion</a>
                            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse" id="navbarNav">
                                <ul class="navbar-nav ml-auto">
                                    <c:if test="${sessionScope.LOGIN_USER != null}">
                                        <li class="nav-item">
                                            <span class="nav-link welcome-message">Welcome,
                                                ${sessionScope.LOGIN_USER.fullName}</span>
                                        </li>
                                        <li class="nav-item">
                                            <form action="MainController" method="POST" class="d-inline">
                                                <button type="submit" name="action" value="Shop"
                                                    class="btn btn-outline-light">Continue Shopping</button>
                                            </form>
                                        </li>
                                        <li class="nav-item">
                                            <form action="MainController" method="POST" class="d-inline">
                                                <button type="submit" name="action" value="Logout"
                                                    class="btn btn-outline-light">Log Out</button>
                                            </form>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.LOGIN_USER == null}">
                                        <li class="nav-item">
                                            <a class="btn btn-outline-light mx-1" href="login.jsp">Login</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="btn btn-outline-light mx-1" href="register.jsp">Register</a>
                                        </li>
                                        <li class="nav-item">
                                            <form action="MainController" method="POST" class="d-inline">
                                                <button class="btn btn-outline-light mx-1" type="submit" name="action"
                                                    value="Shop">Continue Shopping</button>
                                            </form>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </nav>
                    <div class="cart-container">
                        <header class="cart-header">
                            <h1>Your Shopping Cart</h1>
                        </header>
                        <p style="color: red">${requestScope.ERROR}</p>
                        <p style="color: green">${requestScope.SUCCESS}</p>
                        <c:set var="cart" value="${sessionScope.CART}" />
                        <c:if test="${cart == null}">
                            <c:set var="cart" value="<%= new sample.shopping.CartDTO()%>" scope="request" />
                            <div col-lg-4 bg-body-tertiary border border-dark>
                                <h1 style="color: green">Ra kia mà chọn đồ rồi quẹo lại đây!</h1>
                            </div>

                        </c:if>
                        <c:if test="${cart != null}">
                            <div class="row">
                                <!-- Shopping Cart Table -->
                                <div class="col-lg-8">
                                    <table class="table table-striped align-middle mb-0 bg-white border border-dark">
                                        <thead class="bg-light">
                                            <tr>
                                                <th>No</th>
                                                <th>Product ID</th>
                                                <th>Image</th>
                                                <th>Product Name</th>
                                                <th>Quantity</th>
                                                <th>Price</th>
                                                <th>Total</th>
                                                <th>Change</th>
                                                <th>Remove</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="count" value="1" />
                                            <c:set var="total" value="0" />
                                            <c:forEach var="product" items="${cart.cart.values()}">
                                                <c:set var="productTotal" value="${product.price * product.quantity}" />
                                                <c:set var="total" value="${total + productTotal}" />
                                                <tr>
                                                    <form action="MainController" method="POST">
                                                        <td>${count}</td>
                                                        <td>
                                                            ${product.productID}
                                                        </td>
                                                        <td>
                                                            <img src="${product.imageUrl}" alt="${product.productName}"
                                                                style="width: 100px; height: 100px"
                                                                class="img-fluid rounded-3">
                                                        </td>
                                                        <td>${product.productName}</td>
                                                        <td>
                                                            <input style="width: 100px" type="number" min="0"
                                                                name="quantity" value="${product.quantity}"
                                                                required="" />
                                                        </td>
                                                        <td>${product.price}$</td>
                                                        <td>${productTotal}$</td>
                                                        <td>
                                                            <input type="hidden" name="productID"
                                                                value="${product.productID}" />
                                                            <input type="submit" name="action" value="Change"
                                                                class="btn btn-sm btn-info" />
                                                        </td>
                                                        <td>
                                                            <input type="submit" name="action" value="Remove"
                                                                class="btn btn-sm btn-danger" />
                                                        </td>
                                                </tr>
                                                <c:set var="count" value="${count + 1}" />
                                                </form>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- Checkout Summary -->
                                <div class="col-lg-4 bg-body-tertiary border border-dark">
                                    <div class="p-5">
                                        <h3 class="fw-bold mb-5 mt-2 pt-1">Summary</h3>
                                        <hr class="my-4">
                                        <div class="d-flex justify-content-between mb-4">
                                            <h5 class="text-uppercase">Total Items</h5>
                                            <h5>${cart.cart.size()}</h5>
                                        </div>
                                        <div class="d-flex justify-content-between mb-4">
                                            <h5 class="text-uppercase mb-3">Total Price</h5>
                                            <h5 class="mb-5">$${total}</h5>
                                        </div>
                                        <form action="MainController" method="POST">
                                            <input type="hidden" name="amount" value="${total}">
                                            <select name="payMethod">
                                                <option value="cash">Cash</option>
                                                <option value="vnPay">vnPay</option>
                                                <option></option>
                                            </select>
                                            <input type="submit" value="Checkout" name="action"
                                                class="btn btn-dark btn-block btn-lg" />
                                            <button type="submit" value="ViewShop" name="action"
                                                class="btn btn-success btn-block btn-lg">Add More</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                    </div>
                </body>

                </html>