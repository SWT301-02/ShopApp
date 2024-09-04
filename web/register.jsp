<%-- 
    Document   : shop
    Created on : Aug 14, 2024, 11:07:31 AM
    Author     : lmao
--%>

<%@page import="sample.user.UserError" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register User</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- MDB Bootstrap CSS -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.css" rel="stylesheet">
        <!-- Custom Style -->
        <link href="css/style.css" rel="stylesheet">
        <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
        <link href="css/container.css" rel="stylesheet">
    </head>
    <body>
        <c:set var="userError" value="${requestScope.USER_ERROR}" />
        <c:if test="${userError == null}">
            <c:set var="userError" value="<%= new sample.user.UserError()%>" scope="request" />
        </c:if>

        <div class="background-radial-gradient overflow-hidden">
            <div class="container text-center text-lg-start">
                <div class="row gx-lg-5 align-items-center mb-5">
                    <div class="col-lg-6 mb-5 mb-lg-0" style="z-index: 10">
                        <h1 class="my-5 display-5 fw-bold ls-tight" style="color: hsl(218, 81%, 95%)">
                            Welcome to<br />
                            <span style="color: hsl(218, 81%, 75%)">be a member</span>
                        </h1>
                    </div>

                    <div class="col-lg-6 mb-5 mb-lg-0 position-relative">
                        <div id="radius-shape-1" class="position-absolute rounded-circle shadow-5-strong"></div>
                        <div id="radius-shape-2" class="position-absolute shadow-5-strong"></div>

                        <div class="card bg-glass">
                            <div class="card-body px-4 py-5 px-md-5">
                                <h3 class="text-center mb-4">Register</h3>

                                <form action="MainController" method="POST">
                                    <!-- User ID input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="text" id="userID" name="userID" class="form-control" required />
                                        <label class="form-label" for="userID">User ID</label>
                                    </div>
                                    <p style="color: red">${userError.userIDError}</p>

                                    <!-- Full Name input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="text" id="fullName" name="fullName" class="form-control" required value="${requestScope.fullName}" />
                                        <label class="form-label" for="fullName">Full Name</label>
                                    </div>
                                    <p style="color: red">${userError.fullNameError}</p>

                                    <!-- Role selection -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <select id="roleID" name="roleID" class="form-control">
                                            <option value="US">User</option>
                                        </select>
                                    </div>

                                    <!-- Email input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="text" id="email" name="email" class="form-control" required value="${requestScope.email}" />
                                        <label class="form-label" for="email">Email</label>
                                    </div>
                                    <p style="color: red">${userError.emailError}</p>

                                    <!-- Password input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="password" id="password" name="password" class="form-control" required />
                                        <label class="form-label" for="password">Password</label>
                                    </div>

                                    <!-- Confirm Password input -->
                                    <div data-mdb-input-init class="form-outline mb-4">
                                        <input type="password" id="confirm" name="confirm" class="form-control" required />
                                        <label class="form-label" for="confirm">Confirm Password</label>
                                    </div>
                                    <p style="color: red">${userError.confirmError}</p>

                                    <!-- Submit button -->
                                    <button class="btn btn-primary btn-block mb-4" type="submit" name="action" value="Insert">Create Account</button>
                                    <input type="reset" value="Reset" class="btn btn-secondary btn-block mb-4">

                                </form>

                                <a href="login.jsp" class="btn btn-success btn-block mb-4">Back to Login</a>
                                <!-- Error message -->
                                <c:if test="${not empty userError.error}">
                                    <p style="color: red">${userError.error}</p>
                                </c:if>

                                <!-- Success message -->
                                <c:if test="${not empty requestScope.SUCCESS}">
                                    <p style="color: green">${requestScope.SUCCESS}</p>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- MDB Bootstrap JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.js"></script>
    </body>

</html>
