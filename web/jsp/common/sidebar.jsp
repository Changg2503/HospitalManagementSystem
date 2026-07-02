<%-- 
    Document   : sidebar
    Created on : Jul 2, 2026, 5:40:04 PM
    Author     : Nguyen Trang


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
--%>

<div class="sidebar">

    <h3>Hospital System</h3>

    <a href="${pageContext.request.contextPath}/dashboard">
        <i class="fa fa-home"></i>
        Dashboard
    </a>

    <a href="${pageContext.request.contextPath}/patient?action=list">
        <i class="fa fa-user"></i>
        Patient
    </a>

    <a href="${pageContext.request.contextPath}/doctor?action=list">
        <i class="fa fa-user-doctor"></i>
        Doctor
    </a>

    <a href="${pageContext.request.contextPath}/department?action=list">
        <i class="fa fa-building"></i>
        Department
    </a>

    <a href="${pageContext.request.contextPath}/appointment?action=list">
        <i class="fa fa-calendar"></i>
        Appointment
    </a>

    <a href="${pageContext.request.contextPath}/medicalrecord?action=list">
        <i class="fa fa-file-medical"></i>
        Medical Record
    </a>

    <a href="${pageContext.request.contextPath}/report">
        <i class="fa fa-chart-column"></i>
        Report
    </a>

    <a href="${pageContext.request.contextPath}/logout">
        <i class="fa fa-right-from-bracket"></i>
        Logout
    </a>

</div>