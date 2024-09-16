<%-- Document : cart Created on : Aug 14, 2024, 10:54:07 AM Author : lmao --%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@page contentType="text/html" pageEncoding="UTF-8" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Admin Dashboard</title>
                <link rel="icon" type="image/png" sizes="32x32" href="img/logo.png">
                <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
                    rel="stylesheet">
                <link href="css/luxury-shop.css" rel="stylesheet">
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

                    .table img {
                        width: 40px;
                        height: 40px;
                        object-fit: cover;
                        border-radius: 50%;
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
                                            <i class="fas fa-tachometer-alt"></i> Dashboard
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="insert.jsp">
                                            <i class="fas fa-plus-circle"></i> Create User
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="MainController?action=Logout">
                                            <i class="fas fa-sign-out-alt"></i> Logout
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </nav>

                        <!-- Main content -->
                        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4 main-content">
                            <div
                                class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                                <h1 class="h2">Welcome: ${sessionScope.LOGIN_USER.fullName}</h1>
                            </div>

                            <!-- Sales Statistics -->
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Sales Statistics</h5>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="card bg-primary text-white mb-4">
                                                <div class="card-body">
                                                    <h5 class="card-title">Total Sales</h5>
                                                    <h3 class="mb-0">$${totalSales}</h3>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="card bg-success text-white mb-4">
                                                <div class="card-body">
                                                    <h5 class="card-title">Total Orders</h5>
                                                    <h3 class="mb-0">${totalOrders}</h3>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="card bg-info text-white mb-4">
                                                <div class="card-body">
                                                    <h5 class="card-title">Average Order Value</h5>
                                                    <h3 class="mb-0">$${averageOrderValue}</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Top Selling Products -->
                            <div class="card mb-4">
                                <div class="card-body">
                                    <h5 class="card-title">Top Selling Products</h5>
                                    <div class="table-responsive">
                                        <table class="table table-striped table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Product ID</th>
                                                    <th>Product Name</th>
                                                    <th>Total Quantity Sold</th>
                                                    <th>Total Revenue</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="product" items="${topSellingProducts}">
                                                    <tr>
                                                        <td>${product.productID}</td>
                                                        <td>${product.productName}</td>
                                                        <td>${product.quantitySold}</td>
                                                        <td>$${product.totalRevenue}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${requestScope.LIST_USER != null && not empty requestScope.LIST_USER}">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">User List</h5>
                                        <div class="table-responsive">
                                            <table class="table table-striped table-sm">
                                                <thead>
                                                    <tr>
                                                        <th>No.</th>
                                                        <th>UserID</th>
                                                        <th>Name</th>
                                                        <th>Role</th>
                                                        <th>Actions</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="user" varStatus="counter"
                                                        items="${requestScope.LIST_USER}">
                                                        <form action="MainController" method="POST">
                                                            <tr>
                                                                <td>${counter.count}</td>
                                                                <td>${user.userID}</td>
                                                                <td>
                                                                    <img src="https://mdbootstrap.com/img/new/avatars/${counter.count}.jpg"
                                                                        alt="" class="rounded-circle mr-2">
                                                                    <input type="text" name="fullName"
                                                                        value="${user.fullName}" required
                                                                        class="form-control-sm">
                                                                </td>
                                                                <td>
                                                                    <select name="roleID" required
                                                                        class="form-control-sm">
                                                                        <option value="AD" ${user.roleID=='AD'
                                                                            ? 'selected' : '' }>AD</option>
                                                                        <option value="US" ${user.roleID=='US'
                                                                            ? 'selected' : '' }>US</option>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <input type="hidden" name="userID"
                                                                        value="${user.userID}">
                                                                    <input type="hidden" name="search"
                                                                        value="${param.search}">
                                                                    <button type="submit" name="action" value="Update"
                                                                        class="btn btn-sm btn-primary">Update</button>
                                                                    <c:url var="deleteLink" value="MainController">
                                                                        <c:param name="action" value="Delete" />
                                                                        <c:param name="userID" value="${user.userID}" />
                                                                        <c:param name="search"
                                                                            value="${param.search}" />
                                                                    </c:url>
                                                                    <a href="${deleteLink}"
                                                                        class="btn btn-sm btn-danger">Delete</a>
                                                                </td>
                                                            </tr>
                                                        </form>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${not empty requestScope.ERROR}">
                                <div class="alert alert-danger mt-3" role="alert">
                                    ${requestScope.ERROR}
                                </div>
                            </c:if>
                            <c:if test="${not empty requestScope.SUCCESS}">
                                <div class="alert alert-success mt-3" role="alert">
                                    ${requestScope.SUCCESS}
                                </div>
                            </c:if>
                        </main>
                    </div>
                </div>

                <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            </body>

            </html>