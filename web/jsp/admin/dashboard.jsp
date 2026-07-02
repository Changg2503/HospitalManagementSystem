<%-- 
    Document   : dashboard
    Created on : Jun 29, 2026, 3:57:26 PM
    Author     : Nguyen Trang


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
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <h2>Dashboard</h2>

    <div class="row mt-4">

        <div class="col-md-3">

            <div class="card-box">

                <h5>Total Patient</h5>

                <h2>${totalPatient}</h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card-box">

                <h5>Total Doctor</h5>

                <h2>${totalDoctor}</h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card-box">

                <h5>Total Appointment</h5>

                <h2>${totalAppointment}</h2>

            </div>

        </div>

        <div class="col-md-3">

            <div class="card-box">

                <h5>Total Record</h5>

                <h2>${totalRecord}</h2>

            </div>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>