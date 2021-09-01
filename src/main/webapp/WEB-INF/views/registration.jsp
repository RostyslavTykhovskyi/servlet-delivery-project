<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="lang" value="${not empty param.lang ? param.lang : not empty lang ? lang : 'en'}" scope="session" />
<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="messages"/>

<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="title.registration" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
</head>
<body class="d-flex flex-column min-vh-100">
<%@ include file = "fragments/header.jsp" %>

<div class="container" style="width: 500px; margin-top: 50px;">
    <form action="${pageContext.request.contextPath}/registration" method="POST">
        <div class="mb-3">
            <label for="username" class="form-label"><fmt:message key="user.username"/></label>
            <input type="text" class="form-control" id="username" name="username" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('username')}" th:text="#{error.username}">--%>
<%--                Username should be 5-20 characters long.--%>
<%--            </div>--%>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label"><fmt:message key="user.email"/></label>
            <input type="text" class="form-control" id="email" name="email" required>
<%--            <div class="invalid-feedback" th:if="${#fields.hasErrors('email')}" th:text="#{error.email}">--%>
<%--                Invalid email.--%>
<%--            </div>--%>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label"><fmt:message key="user.password"/></label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="button.registration"/></button>
    </form>
</div>

<%@ include file = "fragments/footer.jsp" %>
</body>
</html>