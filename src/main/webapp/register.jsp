<%-- Document : shop Created on : Aug 14, 2024, 11:07:31 AM Author : lmao --%>

    <%@page import="sample.user.UserError" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Register User</title>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;700&family=Montserrat:wght@300;400;600&display=swap"
                        rel="stylesheet">
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <link rel="stylesheet"
                        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
                    <link href="css/luxury-style.css" rel="stylesheet">
                    <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
                </head>

                <body>
                    <c:set var="userError" value="${requestScope.USER_ERROR}" />
                    <c:if test="${userError == null}">
                        <c:set var="userError" value="<%= new sample.user.UserError()%>" scope="request" />
                    </c:if>

                    <div class="luxury-container">
                        <div class="row align-items-center">
                            <div class="col-lg-6 mb-5 mb-lg-0">
                                <h1 class="display-4 mb-4">Join Our Exclusive Club</h1>
                                <p class="lead mb-4">Experience luxury and elegance as a member.</p>
                            </div>

                            <div class="col-lg-6">
                                <div class="card card-luxury">
                                    <div class="card-body p-5">
                                        <h3 class="text-center mb-4">Register</h3>

                                        <form action="MainController" method="POST">
                                            <!-- User ID input -->
                                            <div class="mb-4">
                                                <input type="text" id="userID" name="userID" class="form-control"
                                                    required />
                                                <label class="form-label" for="userID">User ID</label>
                                                <p class="error-message">${userError.userIDError}</p>
                                            </div>

                                            <!-- Full Name input -->
                                            <div class="mb-4">
                                                <input type="text" id="fullName" name="fullName" class="form-control"
                                                    required value="${requestScope.fullName}" />
                                                <label class="form-label" for="fullName">Full Name</label>
                                                <p class="error-message">${userError.fullNameError}</p>
                                            </div>

                                            <!-- Role selection -->
                                            <div class="mb-4">
                                                <select id="roleID" name="roleID" class="form-control">
                                                    <option value="US">User</option>
                                                </select>
                                            </div>

                                            <!-- Email input -->
                                            <div class="mb-4">
                                                <input type="text" id="email" name="email" class="form-control" required
                                                    value="${requestScope.email}" />
                                                <label class="form-label" for="email">Email</label>
                                                <p class="error-message">${userError.emailError}</p>
                                            </div>

                                            <!-- Password input -->
                                            <div class="mb-4">
                                                <input type="password" id="password" name="password"
                                                    class="form-control" required />
                                                <label class="form-label" for="password">Password</label>
                                            </div>

                                            <!-- Confirm Password input -->
                                            <div class="mb-4">
                                                <input type="password" id="confirm" name="confirm" class="form-control"
                                                    required />
                                                <label class="form-label" for="confirm">Confirm Password</label>
                                                <p class="error-message">${userError.confirmError}</p>
                                            </div>

                                            <!-- Submit button -->
                                            <button class="btn btn-luxury btn-block mb-4 w-100" type="submit"
                                                name="action" value="Insert">Create Account</button>
                                            <button type="reset"
                                                class="btn btn-outline-secondary btn-block mb-4 w-100">Reset</button>
                                        </form>

                                        <a href="login.jsp" class="btn btn-outline-luxury btn-block mb-4 w-100">Back to
                                            Login</a>

                                        <!-- Error message -->
                                        <c:if test="${not empty userError.error}">
                                            <p class="error-message text-center">${userError.error}</p>
                                        </c:if>

                                        <!-- Success message -->
                                        <c:if test="${not empty requestScope.SUCCESS}">
                                            <p class="text-success text-center">${requestScope.SUCCESS}</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- MDB Bootstrap JS -->
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.0.0/mdb.min.js"></script>
                </body>

                </html>