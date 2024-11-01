<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Employee List</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/employee/list.css">
    </head>
    <body>
        <jsp:include page="/ui/header.jsp" />
        <div class="container">
            <h1>Employee List For ${department.dname}</h1>
            <table> 
                <tr>
                    <th>Employee ID</th>
                    <th>Full Name</th>
                    <th>Salary Level</th>
                    <th>Hourly Rate</th>
                </tr>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td>${employee.eid}</td>
                        <td>${employee.ename}</td>
                        <td>${employee.salaryLevel}</td>
                        <td class="rate">
                            <c:choose>
                                <c:when test="${employee.salaryLevel == 'F1'}">30K</c:when>
                                <c:when test="${employee.salaryLevel == 'F2'}">40K</c:when>
                                <c:when test="${employee.salaryLevel == 'F3'}">50K</c:when>
                                <c:otherwise>Unknown</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <jsp:include page="/ui/footer.jsp" />
    </body>
</html>
