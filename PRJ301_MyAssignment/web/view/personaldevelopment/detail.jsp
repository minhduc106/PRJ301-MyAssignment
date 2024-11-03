<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personnel Deployment Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/personaldevelopment/detailstyles.css">
</head>
<body>
    <div class="container">
        <h2>Detailed Personnel Deployment Plan for ${date}</h2>
        
        <c:choose>
            <c:when test="${empty workerSchedules}">
                <p class="no-data-message" style="text-align: center; font-size: 18px; color: #333;">
                    No personal development plan exists for ${date}.
                </p>
            </c:when>
            <c:otherwise>
                <table>
                    <tr>
                        <th>Employee ID</th>
                        <th>Employee Name</th>
                        <th>Product Name</th>
                        <th>Product ID</th>
                        <th>Shift</th>
                        <th>Ordered Quantity</th>
                    </tr>
                    <c:forEach var="ws" items="${workerSchedules}">
                        <tr>
                            <td>${ws.employee.eid}</td>
                            <td>${ws.employee.ename}</td>
                            <td>${ws.scheduleCampaign.planCampaign.product.pName}</td> 
                            <td>${ws.scheduleCampaign.planCampaign.product.pID}</td> 
                            <td>${ws.scheduleCampaign.shift}</td> 
                            <td>${ws.quantity}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        
        <!-- Button to navigate to the create page -->
        <div style="text-align: center; margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/personaldevelopment/create?date=${date}" class="btn-create">
                Create Personal Development
            </a>
        </div>
    </div>
</body>
</html>
