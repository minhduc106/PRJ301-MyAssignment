<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Campaign Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/productionplan/detail.css">
</head>
<body>
    <jsp:include page="/ui/header.jsp"></jsp:include>
    <div class="container" style="margin-top: 10px;">
        <h2 class="title">Schedule Campaign Details for Plan ID: ${plan.plid}</h2>
        <p>Department: ${plan.department.dname}</p>

        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Product</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="campaign" items="${completedCampaigns}">
                    <tr>
                        <td>${campaign.date}</td>
                        <td>${campaign.shift}</td>
                        <td>${campaign.product.pName}</td>
                        <td>${campaign.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:include page="/ui/footer.jsp"></jsp:include>
</body>
</html>
