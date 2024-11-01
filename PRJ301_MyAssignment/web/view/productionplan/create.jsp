<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Production Plan</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/productionplan/create.css">
        
    </head>
    
    <body>
        <jsp:include page="../../ui/header.jsp"></jsp:include>
        <form action="create" method="POST"> 
            <h2>Create Production Plan</h2>
            <div class="form-row">
                <label for="from">From:</label>
                <input type="date" name="from" id="from" /> 
                <label for="to">To:</label>
                <input type="date" name="to" id="to"/>
            </div>
            <br/>
            Workshop: <select name="did">
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.did}">${d.dname}</option>
                </c:forEach>
            </select>
            <br/>
            <table>
                <tr>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th>Estimated Effort</th>
                </tr>
                <c:forEach items="${requestScope.products}" var="p">
                <tr>
                    <td>${p.pName}<input type="hidden" name="pid" value="${p.pID}"/></td>
                    <td><input type="text" name="quantity${p.pID}"/></td>
                    <td><input type="text" name="effort${p.pID}"/></td>
                </tr>   
                </c:forEach>
            </table>
            <input type="submit" value="Save"/>
        </form>        
        <jsp:include page="../../ui/footer.jsp"></jsp:include>

    </body>
</html>
