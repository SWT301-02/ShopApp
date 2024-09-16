<%-- Document : user Created on : Aug 7, 2024, 11:40:29 AM Author : lmao --%>

    <%@page import="sample.user.UserDTO" %>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@page contentType="text/html" pageEncoding="UTF-8" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>User Dashboard</title>
                    <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
                    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
                        rel="stylesheet">
                    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
                        rel="stylesheet">
                    <style>
                        body {
                            background-color: #f8f9fa;
                        }

                        .sidebar {
                            min-height: 100vh;
                            background-color: #343a40;
                            color: #fff;
                        }

                        .sidebar .nav-link {
                            color: #fff;
                        }

                        .sidebar .nav-link:hover {
                            background-color: #495057;
                        }

                        .main-content {
                            padding: 20px;
                        }

                        .card {
                            margin-bottom: 20px;
                        }
                    </style>
                </head>

                <body>
                    <div class="container-fluid">
                        <div class="row">
                            <!-- Sidebar -->
                            <nav class="col-md-2 d-none d-md-block sidebar">
                                <div class="sidebar-sticky">
                                    <ul class="nav flex-column">
                                        <li class="nav-item">
                                            <a class="nav-link active" href="#">
                                                <i class="fas fa-home"></i> Dashboard
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="MainController?action=ViewShop">
                                                <i class="fas fa-shopping-bag"></i> Go to Shopping
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <form action="MainController" method="POST" class="d-inline">
                                                <button type="submit" name="action" value="ViewCart"
                                                    class="btn nav-link">
                                                    <i class="fas fa-shopping-cart"></i> View Cart
                                                </button>
                                            </form>
                                        </li>
                                        <li class="nav-item">
                                            <form action="MainController" method="POST" class="d-inline">
                                                <button type="submit" name="action" value="Logout" class="btn nav-link">
                                                    <i class="fas fa-sign-out-alt"></i> Logout
                                                </button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </nav>

                            <!-- Main content -->
                            <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 main-content">
                                <div
                                    class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                    <h1 class="h2">Welcome, ${sessionScope.LOGIN_USER.fullName}!</h1>
                                </div>

                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">User Information</h5>
                                                <p><strong>UserID:</strong> ${sessionScope.LOGIN_USER.userID}</p>
                                                <p><strong>Full Name:</strong> ${sessionScope.LOGIN_USER.fullName}</p>
                                                <p><strong>Role ID:</strong> ${sessionScope.LOGIN_USER.roleID}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5 class="card-title">Quick Actions</h5>
                                                <a href="MainController?action=ViewShop"
                                                    class="btn btn-primary btn-block mb-2">
                                                    <i class="fas fa-shopping-bag"></i> Go to Shopping
                                                </a>
                                                <form action="MainController" method="POST">
                                                    <button type="submit" name="action" value="ViewCart"
                                                        class="btn btn-info btn-block mb-2">
                                                        <i class="fas fa-shopping-cart"></i> View Cart
                                                    </button>
                                                    <button type="submit" name="action" value="Logout"
                                                        class="btn btn-danger btn-block">
                                                        <i class="fas fa-sign-out-alt"></i> Logout
                                                    </button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </main>
                        </div>
                    </div>

                    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
                    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                </body>

                </html>