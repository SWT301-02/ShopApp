<%-- Document : shop Created on : Aug 14, 2024, 11:07:31 AM Author : lmao --%>

    <%@page import="sample.user.UserError" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                    <title>Add New User - Admin Dashboard</title>
                    <!-- Bootstrap CSS -->
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                        rel="stylesheet">
                    <!-- Font Awesome -->
                    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                        rel="stylesheet">
                    <!-- Custom Style -->
                    <link href="css/admin-style.css" rel="stylesheet">
                </head>

                <body>
                    <c:set var="userError" value="${requestScope.USER_ERROR}" />
                    <c:if test="${userError == null}">
                        <c:set var="userError" value="<%= new sample.user.UserError()%>" scope="request" />
                    </c:if>

                    <div class="container admin-container">
                        <div class="admin-header">
                            <h2 class="mb-4">Add New User</h2>
                            <p class="text-muted">Welcome, ${sessionScope.LOGIN_USER.fullName}</p>
                        </div>

                        <form action="MainController" method="POST" class="admin-form">
                            <!-- User ID input -->
                            <div class="mb-3">
                                <label for="userID" class="form-label">User ID</label>
                                <input type="text" id="userID" name="userID" class="form-control" required />
                                <p class="text-danger small">${userError.userIDError}</p>
                            </div>

                            <!-- Full Name input -->
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Full Name</label>
                                <input type="text" id="fullName" name="fullName" class="form-control" required />
                                <p class="text-danger small">${userError.fullNameError}</p>
                            </div>

                            <!-- Role selection -->
                            <div class="mb-3">
                                <label for="roleID" class="form-label">Role</label>
                                <select id="roleID" name="roleID" class="form-select">
                                    <option value="AD">Admin</option>
                                    <option value="US">User</option>
                                </select>
                            </div>

                            <!-- Email input -->
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" id="email" name="email" class="form-control" required />
                            </div>

                            <!-- Password input -->
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" id="password" name="password" class="form-control" required />
                            </div>

                            <!-- Confirm Password input -->
                            <div class="mb-3">
                                <label for="confirm" class="form-label">Confirm Password</label>
                                <input type="password" id="confirm" name="confirm" class="form-control" required />
                                <p class="text-danger small">${userError.confirmError}</p>
                            </div>

                            <!-- Submit and Reset buttons -->
                            <div class="d-grid gap-2">
                                <button class="btn btn-primary" type="submit" name="action" value="Insert">
                                    <i class="fas fa-user-plus me-2"></i>Create Account
                                </button>
                                <button type="reset" class="btn btn-secondary">
                                    <i class="fas fa-undo me-2"></i>Reset
                                </button>
                            </div>

                            <!-- Error and Success messages -->
                            <c:if test="${not empty userError.error}">
                                <div class="alert alert-danger mt-3" role="alert">
                                    ${userError.error}
                                </div>
                            </c:if>
                            <c:if test="${not empty requestScope.SUCCESS}">
                                <div class="alert alert-success mt-3" role="alert">
                                    ${requestScope.SUCCESS}
                                </div>
                            </c:if>
                        </form>

                        <p class="text-center mt-4">
                            <a href="admin.jsp" class="btn btn-link"><i class="fas fa-arrow-left me-2"></i>Back to admin
                                page</a>
                        </p>
                    </div>

                    <!-- Bootstrap JS and Popper.js -->
                    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
                </body>

                </html>