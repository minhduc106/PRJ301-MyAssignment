<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Campaign Details</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/schedulecampaign/liststyles.css">
</head>
<body>
    <jsp:include page="/ui/header.jsp" />
    
    <div class="container" style="margin-top: 10px;">
        <h2>Schedule Campaign Details for Plan ID: ${plid}</h2>
        
        <table class="schedule-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Department</th>
                    <th>Shift</th>
                    <th>Product</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="campaign" items="${scheduleCampaigns}">
                    <tr>
                        <td>${campaign.date}</td>
                        <td>${departmentName}</td>
                        <td>${campaign.shift}</td>
                        <td>${campaign.planCampaign.product.pName}</td>
                        <td>${campaign.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    
    <jsp:include page="/ui/footer.jsp" />
</body>
</html>
