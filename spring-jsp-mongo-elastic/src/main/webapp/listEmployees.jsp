<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%--<h1>Employee List</h1>--%>


<div class="container">
    <h2 class="mt-4">Employee List</h2>
    <table class="table table-bordered table-hover">
        <thead class="thead-light">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col" class="text-center">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty employees}">
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td>${employee.id != null ? employee.id : 'N/A'}</td>
                    <td>${employee.name != null ? employee.name : 'N/A'}</td>
                    <td class="text-center">
                        <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/update?id=${employee.id}">Edit</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/delete/${employee.id}">Delete</a>

                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty employees}">
            <tr>
                <td colspan="3" class="text-center">No employees found.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <a class="btn btn-success" href="${pageContext.request.contextPath}/add">Create New Employee</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
