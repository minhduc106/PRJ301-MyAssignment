<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Production Plan Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/productionplan/detail.css">
</head>
<body>
    <%-- Include Header --%>
    <jsp:include page="/ui/header.jsp" />

    <div class="container">
        <h2 class="title">Production Plan Details for Plan ID: ${plan.plid}</h2>
        
        <table>
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Made Quantity</th>
                    <th>Total Quantity</th>
                    <th>Status</th>
                    <th>Date Range</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="campaign" items="${plan.campaigns}" varStatus="loopStatus">
                    <tr>
                        <td>${campaign.product.pName}</td>
                        <td>${campaign.madeQuantity}</td>
                        <td>${campaign.quantity}</td>
                        <td class="
                            <c:choose>
                                <c:when test="${campaign.madeQuantity >= campaign.quantity}">status-complete</c:when>
                                <c:otherwise>status-ongoing</c:otherwise>
                            </c:choose>">
                            <span class="status-dot"></span>
                            <span class="status-text">
                                <c:choose>
                                    <c:when test="${campaign.madeQuantity >= campaign.quantity}">Completed</c:when>
                                    <c:otherwise>Unfinished</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <c:if test="${loopStatus.first}">
                            <td rowspan="${plan.campaigns.size()}" style="text-align: center; vertical-align: middle;">
                                ${plan.startd} - ${plan.endd}
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <%-- Include Footer --%>
    <jsp:include page="/ui/footer.jsp" />
</body>
</html>
