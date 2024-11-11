<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Create Attendance</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/attendance/create.css">
    </head>
    <body>
        <jsp:include page="../../ui/header.jsp"></jsp:include>
            <div class="container">
                <h2>Create Attendance for ${date} - Shift ${shift}</h2>
            <form action="${pageContext.request.contextPath}/attendance/create" method="post">
                <input type="hidden" name="date" value="${date}">
                <input type="hidden" name="shift" value="${shift}">
                <table>
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Employee Name</th>
                            <th>Products</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${employeeProductMap}">
                            <c:forEach var="product" items="${entry.value}" varStatus="status">
                                <tr>
                                    <c:if test="${status.first}">
                                        <td rowspan="${fn:length(entry.value)}">${entry.key.eid}</td>
                                        <td rowspan="${fn:length(entry.value)}">${entry.key.ename}</td>
                                    </c:if>
                                    <td>${product.pName}</td>
                                    <td><input type="number" name="quantity_${entry.key.eid}_${product.pID}" placeholder="Enter quantity" min="0"></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="submit" class="btn-submit">Submit Attendance</button>
            </form>
        </div>
        <jsp:include page="../../ui/footer.jsp"></jsp:include>
    </body>
</html>
