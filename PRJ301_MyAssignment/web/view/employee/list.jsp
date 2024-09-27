<%-- 
    Document   : list.jsp
    Created on : Sep 26, 2024, 4:05:15 PM
    Author     : Minh Duc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <table border="1px">
            <tr>
                <td>Id</td>
                <td>Name</td>
                <td>Salary Level</td>
                <td>Hourly rate</td>
            <tr/>
            <c:forEach items="${requestScope.emps}" var="e">
                <tr>
                    <td>${e.e_ID}</td>
                    <td>${e.e_Name}</td>
                    <td>${e.e_Level}</td>
                    <td>${e.e_HourlyRate}</td>
                <tr/>
            </c:forEach>    
        </table>
    </body>
</html>
