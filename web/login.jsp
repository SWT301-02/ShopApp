<%-- 
    Document   : shop
    Created on : Aug 14, 2024, 11:07:31 AM
    Author     : lmao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- MDB Bootstrap CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <!-- Style lỏ-->
        <link href="css/style.css" rel="stylesheet">
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link href="css/container.css" rel="stylesheet">
    </head>
    <body>
        <div class="background-radial-gradient overflow-hidden">
            <div class="container text-center text-lg-start">
                <div class="row gx-lg-5 align-items-center mb-5">
                    <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
                        <h1 class="my-5 display-5 fw-bold ls-tight" style="color: hsl(218, 81%, 95%)">
                            Template chôm <br/>
                            <span style="color: hsl(218, 81%, 75%)">trên mạng for sure</span>
                        </h1>
                        <p class="mb-4 opacity-70" style="color: hsl(218, 81%, 85%)">
                            Phong cách! Phong cách!
                        </p>
                    </div>

                    <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
                        <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
                        <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

                        <div class="card bg-glass">
                            <div class="card-body px-4 py-5 px-md-5">
                                <form id="login_form" action="MainController" method="post">
                                    <!-- UserID input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="text" id="userID" name="userID" class="form-control" required />
                                        <label class="form-label" for="userID">UserID</label>
                                    </div>

                                    <!-- Password input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="password" id="password" name="password" class="form-control" required />
                                        <label class="form-label" for="password">Password</label>
                                    </div>

                                    <!-- reCAPTCHA -->
                                    <div class="g-recaptcha mb-4" data-sitekey="6Lc_tiQqAAAAAJe9IYkdWv1xFBquGhC9tG57KqWt"></div>

                                    <!-- Submit button -->
                                    <button class="btn btn-info btn-block mb-4" type="submit" name="action" value="Login">Login</button>
                                    <a href="LoginGoogleController" class="btn btn-primary btn-block mb-4">
                                        <i class="fab fa-google icon"></i> Login with Google
                                    </a>
                                    <a href="register.jsp" class="btn btn-success btn-block mb-4">Register</a>
                                    

                                    <!-- Error message -->
                                    <c:if test="${not empty requestScope.ERROR}">
                                        <p style="color: red">${requestScope.ERROR}</p>
                                    </c:if>
                                </form>
                                <form action="MainController" method="post">
                                    <button class="btn btn-info btn-block mb-4" type="submit" name="action" value="TopUser">TopUser</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- reCAPTCHA API -->
        <script src="https://www.google.com/recaptcha/api.js?" async defer></script>
        <!-- MDB Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.js"></script>
    </body>
</html>
