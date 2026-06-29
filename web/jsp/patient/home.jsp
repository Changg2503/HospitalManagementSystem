<%-- 
    Document   : home
    Created on : Jun 29, 2026, 4:41:46 PM
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
        <h1>Patient Page</h1>

        Welcome:
        ${sessionScope.ACCOUNT.username}

        <br>

        <a href="appointment">
            My Appointment
        </a>

        <br>

        <a href="medicalrecord">
            My Medical Record
        </a>

        <br>

        <a href="logout">
            Logout
        </a>F
    </body>
</html>
