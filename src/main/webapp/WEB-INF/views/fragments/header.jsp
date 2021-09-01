<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-lg">
            <a class="navbar-brand d-flex align-items-center me-5" href="/">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="" width="50" height="50" class="d-inline-block align-text-top">
                <div class="text-white ms-1">Delivery</div>
            </a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/"><fmt:message key="link.home" /></a>
                    </li>
                    <c:if test="${empty sessionScope.username}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login"><fmt:message key="link.login" /></a>
                    </li>
                    </c:if>
                    <c:if test="${empty sessionScope.username}">
                    <li class="nav-item">
                        <a class="nav-link" href="/registration"><fmt:message key="link.registration" /></a>
                    </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.username}">
                    <li class="nav-item">
                        <a class="nav-link" href="/cabinet"><fmt:message key="link.cabinet" /></a>
                    </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.username and sessionScope.userRole == 'ROLE_ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin"><fmt:message key="link.admin" /></a>
                    </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.username}">
                    <li class="nav-item">
                        <a class="nav-link" href="/logout"><fmt:message key="link.logout" /></a>
                    </li>
                    </c:if>
                </ul>
                <div class="d-flex">
                    <a class="nav-link text-white-50" href="?lang=en">EN</a>
                    <a class="nav-link text-white-50" href="?lang=ua">UA</a>
                </div>
            </div>
        </div>
    </nav>
</header>