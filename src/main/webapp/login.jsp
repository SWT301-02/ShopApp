<%-- Document : shop Created on : Aug 14, 2024, 11:07:31 AM Author : lmao --%>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Login Page</title>
                <!-- Updated CSS links -->
                <link
                    href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Montserrat:wght@300;400;600&display=swap"
                    rel="stylesheet">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
                <link href="css/luxury-style.css" rel="stylesheet">
                <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
            </head>

            <body>
                <div class="luxury-container">
                    <div class="row align-items-center">
                        <div class="col-lg-6 mb-5 mb-lg-0">
                            <h1 class="display-4 mb-4">Elegant Simplicity</h1>
                            <p class="lead mb-4">Experience luxury at your fingertips.</p>
                        </div>

                        <div class="col-lg-6">
                            <div class="card card-luxury">
                                <div class="card-body p-5">
                                    <form id="login_form" action="MainController" method="post">
                                        <!-- UserID input -->
                                        <div class="mb-4">
                                            <input type="text" id="userID" name="userID" class="form-control"
                                                required />
                                            <label class="form-label" for="userID">UserID</label>
                                        </div>

                                        <!-- Password input -->
                                        <div class="mb-4">
                                            <input type="password" id="password" name="password" class="form-control"
                                                required />
                                            <label class="form-label" for="password">Password</label>
                                        </div>

                                        <!-- reCAPTCHA -->
<%--                                        <div class="g-recaptcha mb-4"--%>
<%--                                            data-sitekey="6Lc_tiQqAAAAAJe9IYkdWv1xFBquGhC9tG57KqWt"></div>--%>

                                        <!-- Submit button -->
                                        <button class="btn btn-luxury btn-block mb-4 w-100" type="submit" name="action"
                                            value="Login">Login</button>
                                        <a href="LoginGoogleController" class="btn btn-google btn-block mb-4 w-100">
                                            <i class="fab fa-google icon"></i> Login with Google
                                        </a>
                                        <a href="register.jsp"
                                            class="btn btn-register btn-block mb-4 w-100">Register</a>

                                        <!-- Error message -->
                                        <c:if test="${not empty requestScope.ERROR}">
                                            <p style="color: red">${requestScope.ERROR}</p>
                                        </c:if>
                                    </form>
                                    <form action="MainController" method="post">
                                        <button class="btn btn-luxury btn-block mb-4 w-100" type="submit" name="action"
                                            value="TopUser">TopUser</button>
                                    </form>
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