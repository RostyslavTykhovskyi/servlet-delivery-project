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

<div class="container" style="width: 500px; margin-top: 50px; margin-bottom: 50px;">
    <form id="form" action="${pageContext.request.contextPath}/" method="POST">
        <div class="mb-3">
            <label for="route" class="form-label"><fmt:message key="order.route"/></label>
            <select id="route" class="form-select" name="route_id" required>
                <c:forEach var="route" items="${requestScope.routes}">
                    <option value="${route.id}" ${route.id == requestScope.route_id ? 'selected' : ''}>
                            ${route.departurePoint} — ${route.arrivalPoint}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="length" class="form-label"><fmt:message key="order.length"/></label>
            <input type="number" class="form-control" id="length" name="length" value="${requestScope.length}" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('length')}" th:text="#{error.length}">--%>
<%--                Length must be greater than 1.--%>
<%--            </div>--%>
        </div>
        <div class="mb-3">
            <label for="width" class="form-label"><fmt:message key="order.width"/></label>
            <input type="number" class="form-control" id="width" name="width" value="${requestScope.width}" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('width')}" th:text="#{error.width}">--%>
<%--                Width must be greater than 1.--%>
<%--            </div>--%>
        </div>
        <div class="mb-3">
            <label for="height" class="form-label"><fmt:message key="order.height"/></label>
            <input type="number" class="form-control" id="height" name="height" value="${requestScope.height}" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('height')}" th:text="#{error.height}">--%>
<%--                Height must be greater than 1.--%>
<%--            </div>--%>
        </div>
        <div class="mb-3">
            <label for="weight" class="form-label"><fmt:message key="order.weight"/></label>
            <input type="number" class="form-control" id="weight" name="weight" value="${requestScope.weight}" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('weight')}" th:text="#{error.weight}">--%>
<%--                Weight must be greater than 1.--%>
<%--            </div>--%>
        </div>
        <c:if test="${not empty sessionScope.username}">
            <div class="mb-3">
                <label for="address" class="form-label"><fmt:message key="order.address"/></label>
                <input type="text" class="form-control" id="address" name="address" value="${requestScope.address}" required>
    <%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('address')}" th:text="#{error.address}">--%>
    <%--                Address must not be blank.--%>
    <%--            </div>--%>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.username}">
            <div class="mb-3">
                <label for="deliveryDate" class="form-label"><fmt:message key="order.deliveryDate"/></label>
                <input type="date" class="form-control" id="deliveryDate" name="deliveryDate" value="${requestScope.deliveryDate}" min="${requestScope.minDate}" required>
    <%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('deliveryDate')}" th:text="#{error.deliveryDate}">--%>
    <%--                Delivery date must be at least 3 days later than today.--%>
    <%--            </div>--%>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.cost}">
            <div class="fs-3 mb-2">
                <fmt:message key="order.cost"/>: ${requestScope.cost} <fmt:message key="order.currency"/>
            </div>
        </c:if>
        <fmt:message key="button.calculate" var="buttonCalculate"/>
        <input form="form" class="btn btn-primary" type="submit" name="calculate" value="${buttonCalculate}">
        <c:if test="${not empty sessionScope.username}">
            <fmt:message key="button.order" var="buttonOrder"/>
            <input form="form" class="btn btn-primary" type="submit" name="makeOrder" value="${buttonOrder}">
        </c:if>
    </form>
</div>

<%@ include file = "fragments/footer.jsp" %>
</body>
</html>