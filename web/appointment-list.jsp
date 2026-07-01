<%-- 
    Document   : appointment-list
    Created on : Jul 1, 2026, 8:37:13 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Appointment List</title>
    </head>
    <body>
        <h2>Appointment Management</h2>

        <a href="createAppointment">
            Add Appointment
        </a>

        <br><br>

        <table border="1">

            <tr>

                <th>ID</th>
                <th>Patient</th>
                <th>Doctor</th>
                <th>Date</th>
                <th>Status</th>
                <th>Action</th>

            </tr>

            <c:forEach items="${list}" var="a">

                <tr>

                    <td>${a.appointmentID}</td>

                    <td>${a.patientID}</td>

                    <td>${a.doctorID}</td>

                    <td>${a.appointmentDate}</td>

                    <td>${a.status}</td>

                    <td>

                        <a href="${pageContext.request.contextPath}/appointment/update?id=${a.appointmentID}">
                            Edit
                        </a>

                        |

                        <a href="${pageContext.request.contextPath}/appointment/delete?id=${a.appointmentID}">
                            Cancel
                        </a>

                    </td>

                </tr>

            </c:forEach>

        </table>
    </body>
</html>
