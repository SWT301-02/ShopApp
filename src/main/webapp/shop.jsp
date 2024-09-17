<%-- Document : shop Created on : Aug 14, 2024, 11:07:31 AM Author : lmao --%>

    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Luxury Fashion Shop</title>
                <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
                <link
                    href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Montserrat:wght@300;400;600&display=swap"
                    rel="stylesheet">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
                <link rel="stylesheet" href="css/shop.css">
            </head>

            <body>
                <!-- Navigation Bar -->
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
                                            <button type="submit" name="action" value="ViewCart"
                                                class="btn btn-outline-light">View Cart</button>
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
                                                value="ViewCart">View Cart</button>
                                        </form>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </nav>

                <div class="shop-container">
                    <header class="shop-header">
                        <h1>Luxury Fashion Collection</h1>
                    </header>

                    <form action="MainController" method="GET" class="search-form">
                        <input type="text" class="search-input" name="search" value="${param.search}"
                            placeholder="Search products...">
                        <button type="submit" name="action" value="SearchProduct" class="search-button">Search</button>
                    </form>

                    <c:if test="${requestScope.LIST_PRODUCT != null && not empty requestScope.LIST_PRODUCT}">
                        <p class="error-message">${requestScope.ERROR}</p>
                        <p class="success-message">${requestScope.SUCCESS}</p>
                        <div class="product-grid">
                            <c:forEach var="product" items="${requestScope.LIST_PRODUCT}">
                                <div class="product-card">
                                    <form action="MainController" method="POST">
                                        <div class="product-image-container">
                                            <img src="${product.imageUrl}" alt="${product.productName}"
                                                class="product-image">
                                        </div>
                                        <h2 class="product-name">${product.productName}</h2>
                                        <p class="product-price">$${product.price}</p>
                                        <p class="product-stock">In Stock: ${product.quantity}</p>
                                        <div class="product-actions">
                                            <input type="number" name="select" value="1" min="1"
                                                max="${product.quantity}" class="quantity-input" required />
                                            <input type="hidden" name="productID" value="${product.productID}" />
                                            <input type="hidden" name="productName" value="${product.productName}" />
                                            <input type="hidden" name="price" value="${product.price}" />
                                            <input type="hidden" name="imageUrl" value="${product.imageUrl}" />
                                            <input type="hidden" name="search" value="${param.search}" />
                                            <button type="submit" name="action" value="Add"
                                                class="add-to-cart-button">Add to Cart</button>
                                        </div>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
            </body>

            </html>