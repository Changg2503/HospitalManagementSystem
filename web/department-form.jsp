<%-- 
    Document   : department-form
    Created on : Jul 1, 2026, 8:35:34 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Department Form</title>
    </head>
    <body>
        <h2>Department Form</h2>

        <form method="post">

            <input type="hidden"
                   name="departmentID"
                   value="${d.departmentID}">

            Name:

            <input type="text"
                   name="departmentName"
                   value="${d.departmentName}">

            <br><br>

            <input type="submit"
                   value="Save">

        </form>

        <br>

        <a href="department">
            Back
        </a>
    </body>
</html>
