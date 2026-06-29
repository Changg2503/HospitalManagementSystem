<%-- 
    Document   : login
    Created on : Jun 29, 2026, 3:39:20 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="login" method="post">

            Username:
            <input type="text" name="username">

            <br>

            Password:
            <input type="password" name="password">

            <br>

            <button type="submit">
                Login
            </button>

            <p style="color:red">
                ${ERROR}
            </p>

        </form>
    </body>
</html>
