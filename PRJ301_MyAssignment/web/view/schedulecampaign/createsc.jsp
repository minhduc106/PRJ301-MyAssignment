<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Schedule Campaign</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/schedulecampaign/createstyles.css">
</head>
<body>
    
    <jsp:include page="../../ui/header.jsp"></jsp:include>
    
    <div class="container">
        <h2>Create Schedule Campaign for Plan ID: ${plan.plid}</h2>
        
        <!-- Added form with action to the doPost method of the controller -->
        <form action="${pageContext.request.contextPath}/schedulecampaign/create" method="post">
            <input type="hidden" name="plid" value="${plan.plid}" />
            <table class="schedule-table">
                <thead>
                    <tr>
                        <th rowspan="2" class="product-header">Product</th>
                        <c:forEach var="date" items="${dates}">
                            <th colspan="3">${date}</th>
                        </c:forEach>
                    </tr>
                    <tr>
                        <c:forEach var="date" items="${dates}">
                            <th>K1</th>
                            <th>K2</th>
                            <th>K3</th>
                        </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td class="product-header">${product.pName}</td>
                            <c:forEach var="date" items="${dates}">
                                <td><input type="text" name="quantity_${product.pID}_K1_${date}" class="input-cell" placeholder="K1" /></td>
                                <td><input type="text" name="quantity_${product.pID}_K2_${date}" class="input-cell" placeholder="K2" /></td>
                                <td><input type="text" name="quantity_${product.pID}_K3_${date}" class="input-cell" placeholder="K3" /></td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <button type="submit" class="create-button">Create Schedule Campaign</button>
        </form>
    </div>
            
    <jsp:include page="../../ui/footer.jsp"></jsp:include>  
</body>
</html>
