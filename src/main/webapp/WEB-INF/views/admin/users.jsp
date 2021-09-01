<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : 'en'}" scope="session"/>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.users"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file="../fragments/header.jsp" %>

<div class="container-lg d-flex flex-column align-items-center">
    <h1 style="margin-top: 50px;"><fmt:message key="users"/></h1>

    <c:if test="${requestScope.users.size() == 0}">
        <h3><fmt:message key="empty"/></h3>
    </c:if>

    <c:if test="${requestScope.users.size() > 0}">
        <table class="table table-hover align-middle" style="width: 800px;">
            <thead>
            <tr>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page}&sortField=user_id&sortDirection=${requestScope.reverseSortDir}">
                            Id${requestScope.sortField != 'user_id' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page}&sortField=username&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="user.username"/>${requestScope.sortField != 'username' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page}&sortField=email&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="user.email"/>${requestScope.sortField != 'email' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page}&sortField=balance&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="user.balance"/>${requestScope.sortField != 'balance' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col"><fmt:message key="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${requestScope.users}">
                <tr>
                    <th scope="row">${user.id}</th>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${user.balance}</td>
                    <td>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/users/${user.id}">Update</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${requestScope.pageNumber > 1}">
        <nav>
            <ul class="pagination">
                <li class=${requestScope.page == 1 ? "page-item disabled" : "page-item"}>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page - 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${requestScope.pageNumber}" var="i">
                    <li class=${i == requestScope.page ? "page-item active" : "page-item"}>
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/admin/users?page=${i}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}">${i}</a>
                    </li>
                </c:forEach>
                <li class=${requestScope.page == requestScope.pageNumber ? "page-item disabled" : "page-item"}>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/admin/users?page=${requestScope.page + 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</div>

<%@ include file="../fragments/footer.jsp" %>
</body>
</html>