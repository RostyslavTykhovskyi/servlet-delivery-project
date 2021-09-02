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
            <input type="number" step="0.01" class="${not empty requestScope.errors['length'] ? 'form-control is-invalid' : 'form-control'}" id="length" name="length" value="${requestScope.length}" min="${requestScope.minDimension}" max="${requestScope.maxDimension}" required>
            <c:if test="${not empty requestScope.errors['length']}">
                <div class="invalid-feedback">
                    <fmt:message key="error.length"/> ${requestScope.minDimension} - ${requestScope.maxDimension} <fmt:message key="unitOfLength"/>
                </div>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="width" class="form-label"><fmt:message key="order.width"/></label>
            <input type="number" step="0.01" class="${not empty requestScope.errors['width'] ? 'form-control is-invalid' : 'form-control'}" id="width" name="width" value="${requestScope.width}" min="${requestScope.minDimension}" max="${requestScope.maxDimension}" required>
            <c:if test="${not empty requestScope.errors['width']}">
                <div class="invalid-feedback">
                    <fmt:message key="error.width"/> ${requestScope.minDimension} - ${requestScope.maxDimension} <fmt:message key="unitOfLength"/>
                </div>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="height" class="form-label"><fmt:message key="order.height"/></label>
            <input type="number" step="0.01" class="${not empty requestScope.errors['height'] ? 'form-control is-invalid' : 'form-control'}" id="height" name="height" value="${requestScope.height}" min="${requestScope.minDimension}" max="${requestScope.maxDimension}" required>
            <c:if test="${not empty requestScope.errors['height']}">
                <div class="invalid-feedback">
                    <fmt:message key="error.height"/> ${requestScope.minDimension} - ${requestScope.maxDimension} <fmt:message key="unitOfLength"/>
                </div>
            </c:if>
        </div>
        <div class="mb-3">
            <label for="weight" class="form-label"><fmt:message key="order.weight"/></label>
            <input type="number" step="0.01" class="${not empty requestScope.errors['weight'] ? 'form-control is-invalid' : 'form-control'}" id="weight" name="weight" value="${requestScope.weight}" min="${requestScope.minWeight}" max="${requestScope.maxWeight}" required>
            <c:if test="${not empty requestScope.errors['weight']}">
                <div class="invalid-feedback">
                    <fmt:message key="error.weight"/> ${requestScope.minWeight} - ${requestScope.maxWeight} <fmt:message key="unitOfMass"/>
                </div>
            </c:if>
        </div>
        <c:if test="${not empty sessionScope.username}">
            <div class="mb-3">
                <label for="address" class="form-label"><fmt:message key="order.address"/></label>
                <input type="text" class="${not empty requestScope.errors['address'] ? 'form-control is-invalid' : 'form-control'}" id="address" name="address" value="${requestScope.address}" required>
                <c:if test="${not empty requestScope.errors['address']}">
                    <div class="invalid-feedback">
                        <fmt:message key="error.address"/>
                    </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.username}">
            <div class="mb-3">
                <label for="deliveryDate" class="form-label"><fmt:message key="order.deliveryDate"/></label>
                <input type="date" class="${not empty requestScope.errors['deliveryDate'] ? 'form-control is-invalid' : 'form-control'}" id="deliveryDate" name="deliveryDate" value="${requestScope.deliveryDate}" min="${requestScope.minDate}" max="${requestScope.maxDate}" required>
                <c:if test="${not empty requestScope.errors['deliveryDate']}">
                    <div class="invalid-feedback">
                        <fmt:message key="error.deliveryDate"/> ${requestScope.minDate} - ${requestScope.maxDate}
                    </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.cost}">
            <div class="fs-3 mb-2">
                <fmt:message key="order.cost"/>: ${requestScope.cost} <fmt:message key="currency"/>
            </div>
        </c:if>
        button
    </form>
</div>

<%@ include file = "fragments/footer.jsp" %>
</body>
</html>