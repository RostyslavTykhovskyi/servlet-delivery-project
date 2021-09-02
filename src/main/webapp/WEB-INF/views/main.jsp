<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : 'en'}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages"/>

<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.home" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file = "fragments/header.jsp" %>

<div class="container-lg d-flex flex-column align-items-center">
    <h1 style="margin-top: 50px;"><fmt:message key="routes"/></h1>

    <c:if test="${requestScope.routes.size() == 0}">
        <h3><fmt:message key="empty"/></h3>
    </c:if>

    <c:if test="${requestScope.routes.size() > 0}">
        <table class="table table-hover align-middle" style="width: 800px;">
            <thead>
            <tr>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/?page=${requestScope.page}&sortField=route_id&sortDirection=${requestScope.reverseSortDir}">
                        Id${requestScope.sortField != 'route_id' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/?page=${requestScope.page}&sortField=departure_point&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="route.departure"/>${requestScope.sortField != 'departure_point' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/?page=${requestScope.page}&sortField=arrival_point&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="route.arrival"/>${requestScope.sortField != 'arrival_point' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/?page=${requestScope.page}&sortField=length&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="route.length"/>${requestScope.sortField != 'length' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col"><fmt:message key="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="route" items="${requestScope.routes}">
                <tr>
                    <th scope="row">${route.id}</th>
                    <td>${route.departurePoint}</td>
                    <td>${route.arrivalPoint}</td>
                    <td>${route.length}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/order" style="margin: 0" method="GET">
                            <input type="hidden" name="route_id" value="${route.id}">
                            <button class="btn btn-primary" type="submit"><fmt:message key="button.order"/></a>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${requestScope.pageNumber > 1}">
        <nav>
            <ul class="pagination">
                <li ${requestScope.page == 1 ? 'class="page-item disabled"' : 'class="page-item"'}>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/?page=${requestScope.page - 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${requestScope.pageNumber}" var="i">
                    <li ${i == requestScope.page ? 'class="page-item active"' : 'class="page-item"'}>
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/?page=${i}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}">${i}</a>
                    </li>
                </c:forEach>
                <li ${requestScope.page == requestScope.pageNumber ? 'class="page-item disabled"' : 'class="page-item"'}>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/?page=${requestScope.page + 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</div>

<%@ include file = "fragments/footer.jsp" %>
</body>
</html>