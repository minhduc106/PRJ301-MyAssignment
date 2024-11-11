<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Select Date and Shift</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/attendance/detailstyles.css">
    </head>
    <body>
        <jsp:include page="../../ui/header.jsp"></jsp:include>
            <div class="container">
                <h2>Select Date and Shift for Attendance Detail</h2>
                <form action="${pageContext.request.contextPath}/attendance/detail" method="get">
                <div class="form-group">
                    <label for="dateSelect">Select Date:</label>
                    <input type="date" id="dateSelect" name="date" value="${param.date}" required onchange="this.form.submit()">
                </div>

                <div class="form-group">
                    <label for="shiftSelect">Select Shift:</label>
                    <select id="shiftSelect" name="shift">
                        <c:forEach var="shift" items="${shifts}">
                            <option value="${shift.shift}">${shift.shift}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn-submit">View Attendance</button>
            </form>



            <c:if test="${not empty attendances}">
                <h2>Attendance at ${dp.dname} at ${date} of Production shift ${shift}</h2>
                <table>
                    <tr>
                        <th>Employee ID</th>
                        <th>Full Name</th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Ordered Quantity</th>
                        <th>Actual Quantity</th>
                        <th>Alpha</th>
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
                                    <c:when test="${attendance.alpha > 1}">Vuot han muc</c:when>
                                    <c:when test="${attendance.alpha < 1}">Thieu san pham</c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <div class="form-group" style="text-align: center; margin-top: 20px;">
                <button type="button" class="btn-create" onclick="redirectToCreate()">Create Attendance</button>
            </div>
            <jsp:include page="../../ui/footer.jsp"></jsp:include>

                <script>
                    function redirectToCreate() {
                        var date = document.getElementById('dateSelect').value;
                        var shift = document.getElementById('shiftSelect').value;
                        if (date && shift) {
                            window.location.href = '${pageContext.request.contextPath}/attendance/create?date=' + date + '&shift=' + shift;
                        } else {
                            alert('Please select both a date and a shift.');
                        }
                    }
            </script>

        </div>
    </body>
</html>
