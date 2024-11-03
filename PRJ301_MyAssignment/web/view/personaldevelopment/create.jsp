<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Worker Schedule</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/personaldevelopment/create.css">
</head>
<body>
    <div class="container">
        <h2>Create Worker Schedule for ${date}</h2>
        <form action="${pageContext.request.contextPath}/personaldevelopment/create" method="post">
            <input type="hidden" name="date" value="${date}">
            
            <div class="form-group">
                <label for="planSelect">Select Plan:</label>
                <select id="planSelect" name="planId">
                    <c:forEach var="plan" items="${plans}">
                        <option value="${plan.plid}">${plan.plid} - ${plan.startd} to ${plan.endd}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="shiftSelect">Select Shift:</label>
                <select id="shiftSelect" name="shift">
                    <c:forEach var="shift" items="${shifts}">
                        <option value="${shift.shift}">${shift.shift}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>Products:</label>
                <table>
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${products}">
                            <tr>
                                <td>
                                    <input type="hidden" name="productId_${product.pID}" value="${product.pID}">
                                    ${product.pName}
                                </td>
                                <td>
                                    <input type="number" name="quantity_${product.pID}" placeholder="Enter quantity" min="0">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="form-group">
                <label for="employeeSelect">Select Employee:</label>
                <select id="employeeSelect" name="employeeId">
                    <c:forEach var="employee" items="${employees}">
                        <option value="${employee.eid}">${employee.ename}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn-submit">Create WorkSchedule</button>
        </form>
    </div>
</body>
</html>
