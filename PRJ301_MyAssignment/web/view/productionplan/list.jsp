<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Production Plan List</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/productionplan/liststyles.css">
</head>
<body>
    <%-- Include Header --%>
    <jsp:include page="/ui/header.jsp" />

    <div class="container">
        <h2 class="title">Production Plan List ${dp.dname}</h2>
        <table>
            <thead>
                <tr>
                    <th>Plan ID</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th>Schedule Campaign</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="plan" items="${plans}">
                    <tr>
                        <td>${plan.plid}</td>
                        <td>${plan.startd}</td>
                        <td>${plan.endd}</td>
                        <td class="
                            <c:choose>
                                <c:when test="${plan.status == 'On-going'}">status-ongoing</c:when>
                                <c:when test="${plan.status == 'Late'}">status-late</c:when>
                                <c:when test="${plan.status == 'Complete'}">status-complete</c:when>
                            </c:choose>">
                            <span class="status-dot"></span>
                            <span class="status-text">${plan.status}</span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/schedulecampaign/create?plid=${plan.plid}" class="create-button">Create</a>
                            <a href="${pageContext.request.contextPath}/schedulecampaign/list?plid=${plan.plid}" class="detail-button">Detail</a>
                            
                        </td>
                        <td><a href="${pageContext.request.contextPath}/productionplan/detail?plid=${plan.plid}" class="detail-button">Plan Detail</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <%-- Include Footer --%>
    <jsp:include page="/ui/footer.jsp" />
</body>
</html>
