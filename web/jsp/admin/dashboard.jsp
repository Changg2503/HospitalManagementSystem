<%-- 
    Document   : dashboard
    Created on : Jun 29, 2026, 3:57:26 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hospital Management System</h1>

        <h3>
            Welcome:
            ${sessionScope.ACCOUNT.username}
        </h3>

        <h3>
            Role:
            ${sessionScope.ACCOUNT.role}
        </h3>

        <a href="logout">
            Logout
        </a>
    </body>
</html>
