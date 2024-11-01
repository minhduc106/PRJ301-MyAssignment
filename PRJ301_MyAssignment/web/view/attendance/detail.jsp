<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Attendance Detail</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/attendance/detail.css">
</head>
<body>    
    <div class="container">
        <table  >
            <h2>Attendance at ${dp.dname} – ${date} – Production shift No. ${shift}</h2>
            <tr>
                <th>Employee ID</th>
                <th>Full Name</th>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Ordered Quantity</th>
                <th>Actual Quantity</th>
                <th>anpha</th>
                <th>Note</th>
            </tr>
            <c:forEach var="attendance" items="${attendances}">
                <tr>
                    <td>${attendance.workerSchedule.employee.eid}</td>
                    <td>${attendance.workerSchedule.employee.ename}</td>
                    <td>${attendance.workerSchedule.scheduleCampaign.planCampaign.product.pID}</td>
                    <td>${attendance.workerSchedule.scheduleCampaign.planCampaign.product.pName}</td>
                    <td>${attendance.workerSchedule.quantity}</td>
                    <td>${attendance.quantity}</td>
                    <td>${attendance.alpha}</td>    
                    <td>
                        <c:choose>
                            <c:when test="${attendance.alpha > 1}">Làm vượt hạn mức</c:when>
                            <c:when test="${attendance.alpha < 1}">Làm thiếu sản phẩm</c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
