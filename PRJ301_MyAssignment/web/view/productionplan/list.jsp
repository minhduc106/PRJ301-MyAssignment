<%-- 
    Document   : list
    Created on : Oct 22, 2024, 12:16:20 PM
    Author     : Minh Duc
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Production Plan List</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/productionplan/list.css">
    </head>
    <body>
        <jsp:include page="../../ui/header.jsp"></jsp:include>
        <div class="container">
            <h2 class="title">Production Plan List</h2>
            <table>
                <thead>
                    <tr>
                        <th>Plan ID</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Total Quantity</th>
                        <th>Delivered Quantity</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="plan" items="${requestScope.plans}">
                        <tr>
                            <td>${plan.plid}</td>
                            <td>${plan.startd}</td>
                            <td>${plan.endd}</td>
                            <td>${plan.totalQuantity}</td>
                            <td>${plan.deliveredQuantity}</td>
                            <td class="
                                <c:choose>
                                    <c:when test="${plan.status == 'On-going'}">status-ongoing</c:when>
                                    <c:when test="${plan.status == 'Late'}">status-late</c:when>
                                    <c:when test="${plan.status == 'Complete'}">status-complete</c:when>
                                </c:choose>">
                                <span class="status-dot"></span>
                                <span class="status-text">${plan.status}</span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <jsp:include page="../../ui/footer.jsp"></jsp:include>
    </body>
</html>
