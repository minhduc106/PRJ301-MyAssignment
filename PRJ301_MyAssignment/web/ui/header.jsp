<%-- 
    Document   : header
    Created on : Oct 20, 2024, 4:04:02 PM
    Author     : Minh Duc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ui/headerstyles.css">
<header>
    <nav>
        <ul>
            <c:forEach items="${sessionScope.features}" var="feature">
                <li><a href="${feature.url}">${feature.name}</a></li>
            </c:forEach>
        </ul>
        <a href="${pageContext.request.contextPath}/logout" class="logout">Logout</a>
    </nav>
</header>



