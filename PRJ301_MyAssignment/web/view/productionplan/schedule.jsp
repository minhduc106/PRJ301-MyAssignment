<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Schedule Production Plan</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/productionplan/schedule.css">
    <script>
        function loadSchedule() {
            const monthYear = document.getElementById("monthYear").value;
            const currentUrl = new URL(window.location.href);
            currentUrl.searchParams.set("monthYear", monthYear);
            window.location.href = currentUrl.toString();
        }
    </script>
</head>
<body>
    <div class="container">
        <h2>Schedule Production Plan</h2>
        
        <!-- Month/Year Selection -->
        <div class="calendar-header">
            <label for="monthYear">Select Schedule:</label>
            <select id="monthYear" name="monthYear" onchange="loadSchedule()">
                <c:forEach var="monthYear" items="${monthsYears}">
                    <option value="${monthYear}" ${monthYear == selectedMonthYear ? "selected" : ""}>${monthYear}</option>
                </c:forEach>
            </select>
        </div>
        
        <!-- Calendar View -->
        <table>
            <tr>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
                <th>Sunday</th>
            </tr>
            <c:set var="dayCounter" value="1" />
            <c:forEach var="week" begin="1" end="6">
                <tr>
                    <c:forEach var="dayOfWeek" begin="1" end="7">
                        <c:choose>
                            <c:when test="${week == 1 && dayOfWeek < startDayOfWeek}">
                                <td></td>
                            </c:when>
                            <c:when test="${dayCounter <= daysInMonth}">
                                <td>
                                    <a href="${pageContext.request.contextPath}/personaldevelopment/detail?date=${fn:substringBefore(selectedMonthYear, '-')}-${fn:substringAfter(selectedMonthYear, '-')}-${dayCounter < 10 ? '0' : ''}${dayCounter}">
                                        ${dayCounter}
                                    </a>
                                </td>
                                <c:set var="dayCounter" value="${dayCounter + 1}" />
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
