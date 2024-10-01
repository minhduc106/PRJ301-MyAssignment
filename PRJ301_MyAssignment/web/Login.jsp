<%-- 
    Document   : Login
    Created on : Oct 1, 2024, 10:57:37 AM
    Author     : Minh Duc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="style.css"/>
    </head>
    <body>
        <form action="login" method="POST" class="login">
            <h2>LOGIN</h2>
            <label for="username">Username:</label>
            <input type="text" name="username"/> <br/>
            <label for="password">Password:</label>
            <input type="password" name="password"/> <br/>
            <input type="submit" value="Login" id="submit">
        </form>
    </body>
</html>
