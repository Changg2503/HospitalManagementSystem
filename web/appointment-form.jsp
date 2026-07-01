<%-- 
    Document   : appointment-form
    Created on : Jul 1, 2026, 8:37:47 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Appointment Form</title>
    </head>
    <body>
        <h2>Appointment Form</h2>

        <form method="post">

            <input type="hidden"
                   name="appointmentID"
                   value="${a.appointmentID}">

            Patient ID:

            <input type="number"
                   name="patientID"
                   value="${a.patientID}"
                   required>

            <br><br>

            Doctor ID:

            <input type="number"
                   name="doctorID"
                   value="${a.doctorID}"
                   required>

            <br><br>

            Appointment Date:

            <input type="datetime-local"
                   name="appointmentDate"
                   required>

            <br><br>

            Status:

            <select name="status">

                <option value="Scheduled">
                    Scheduled
                </option>

                <option value="Completed">
                    Completed
                </option>

                <option value="Cancelled">
                    Cancelled
                </option>

            </select>

            <br><br>

            <input type="submit"
                   value="Save">

        </form>

        <br>

        <a href="${pageContext.request.contextPath}/appointment">
            Back
        </a>
    </body>
</html>
