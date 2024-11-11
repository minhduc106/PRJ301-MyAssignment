<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Worker Schedule</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/personaldevelopment/styles.css">
</head>
<body>
    <div class="container">
        <h2>Create Worker Schedule for ${date}</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/personaldevelopment/create" method="post">
            <input type="hidden" name="date" value="${date}">

            <!-- Select Plan -->
            <div class="form-group">
                <label for="plan">Select Plan:</label>
                <select name="planId" id="plan">
                    <c:forEach var="plan" items="${plans}">
                        <option value="${plan.plid}">${plan.plid} - ${plan.startd} to ${plan.endd}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Select Shift -->
            <div class="form-group">
                <label for="shift">Select Shift:</label>
                <select name="shift" id="shift">
                    <c:forEach var="s" items="${shifts}">
                        <option value="${s.shift}">${s.shift}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Product and Quantity Table -->
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
                            <td>${product.pName}</td>
                            <td><input type="number" name="quantity_${product.pID}" placeholder="Enter quantity" min="1"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <button type="submit" class="btn-submit">Create Worker Schedule</button>
        </form>
    </div>
</body>
</html>
