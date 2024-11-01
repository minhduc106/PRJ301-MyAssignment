<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personnel Deployment Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/personaldevelopment/detail.css">
</head>
<body>
    <div class="container">
        <h2>Detailed Personnel Deployment Plan for ${date}</h2>
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
                    <td>${shift}</td> 
                    <td>${ws.quantity}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
