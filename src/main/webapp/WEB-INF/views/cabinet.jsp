<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : 'en'}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages"/>

<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.cabinet" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file = "fragments/header.jsp" %>

<div class="container-lg d-flex flex-column align-items-center">
    <div class="d-flex align-items-center" style="margin-top: 50px;">
        <div class="me-2"><fmt:message key="user.balance"/>: ${requestScope.balance} <fmt:message key="order.currency"/></div>
        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#topUpModal">
            <fmt:message key="button.topup"/>
        </button>
        <div class="modal fade" id="topUpModal" tabindex="-1" aria-labelledby="topUpModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="topUpModalLabel"><fmt:message key="modal.topup"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="topUpForm" action="${pageContext.request.contextPath}/cabinet/topUp" method="POST">
                            <div class="mb-3">
                                <label for="amount" class="form-label"><fmt:message key="balance.amount"/></label>
                                <input type="number" class="form-control" id="amount" name="amount" min="1" required>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="modal.close"/></button>
                        <button form="topUpForm" type="submit" class="btn btn-primary"><fmt:message key="button.topup"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <h1 style="margin-top: 50px;"><fmt:message key="orders"/></h1>

    <c:if test="${requestScope.orders.size() == 0}">
        <h3><fmt:message key="empty"/></h3>
    </c:if>

    <c:if test="${requestScope.orders.size() > 0}">
        <table class="table table-hover align-middle" style="width: 800px;">
            <thead>
            <tr>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=order_id&sortDirection=${requestScope.reverseSortDir}">
                        Id${requestScope.sortField != 'order_id' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=cost&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="order.cost"/>${requestScope.sortField != 'cost' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=departure_point,arrival_point&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="order.route"/>${requestScope.sortField != 'departure_point,arrival_point' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=address&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="order.address"/>${requestScope.sortField != 'address' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=delivery_date&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="order.deliveryDate"/>${requestScope.sortField != 'delivery_date' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col">
                    <a class="link-dark text-decoration-none" href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page}&sortField=status&sortDirection=${requestScope.reverseSortDir}">
                        <fmt:message key="order.status"/>${requestScope.sortField != 'status' ? '' : requestScope.sortDirection == 'DESC' ? '▾' : '▴'}
                    </a>
                </th>
                <th scope="col"><fmt:message key="actions"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${requestScope.orders}">
                <tr>
                    <th scope="row">${order.id}</th>
                    <td>${order.cost}</td>
                    <td>${order.route.departurePoint} — ${order.route.arrivalPoint}</td>
                    <td>${order.address}</td>
                    <td>${order.deliveryDate}</td>
                    <td>${order.status.name}</td>
                    <td>
                        <c:if test="${order.status.name == 'Awaiting payment'}">
                            <form action="${pageContext.request.contextPath}/cabinet/payForOrder" method="POST">
                                <input type="hidden" name="id" value="${order.id}">
                                <button class="btn btn-primary" type="submit"><fmt:message key="button.pay"/></button>
                            </form>
                        </c:if>
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
                       href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page - 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="1" end="${requestScope.pageNumber}" var="i">
                    <li class=${i == requestScope.page ? "page-item active" : "page-item"}>
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/cabinet?page=${i}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}">${i}</a>
                    </li>
                </c:forEach>
                <li class=${requestScope.page == requestScope.pageNumber ? "page-item disabled" : "page-item"}>
                    <a class="page-link"
                       href="${pageContext.request.contextPath}/cabinet?page=${requestScope.page + 1}&sortField=${requestScope.sortField}&sortDirection=${requestScope.sortDirection}"
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </c:if>
</div>

<%@ include file = "fragments/footer.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
</body>
</html>