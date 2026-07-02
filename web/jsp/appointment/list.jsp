<%-- 
    Document   : list
    Created on : Jul 2, 2026, 5:19:12 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="d-flex justify-content-between mb-3">
        <h2>Appointment Management</h2>

        <a href="appointment?action=add"
           class="btn btn-success">
            Add Appointment
        </a>
    </div>

    <table class="table table-bordered table-hover">

        <thead class="table-dark">

        <tr>
            <th>ID</th>
            <th>Patient ID</th>
            <th>Doctor ID</th>
            <th>Appointment Date</th>
            <th>Status</th>
            <th width="180">Action</th>
        </tr>

        </thead>

        <tbody>

        <c:forEach items="${list}"
                   var="a">

            <tr>

                <td>${a.appointmentID}</td>
                <td>${a.patientID}</td>
                <td>${a.doctorID}</td>
                <td>${a.appointmentDate}</td>
                <td>${a.status}</td>

                <td>

                    <a href="appointment?action=edit&id=${a.appointmentID}"
                       class="btn btn-warning btn-sm">
                        Edit
                    </a>

                    <form action="appointment"
                          method="post"
                          style="display:inline">

                        <input type="hidden"
                               name="action"
                               value="delete">

                        <input type="hidden"
                               name="id"
                               value="${a.appointmentID}">

                        <button class="btn btn-danger btn-sm"
                                onclick="return confirm('Delete this appointment?')">
                            Delete
                        </button>

                    </form>

                </td>

            </tr>

        </c:forEach>

        </tbody>

    </table>

    <nav>

        <ul class="pagination">

            <c:forEach begin="1"
                       end="${endPage}"
                       var="i">

                <li class="page-item
                    ${currentPage==i?'active':''}">

                    <a class="page-link"
                       href="appointment?action=list&page=${i}">
                        ${i}
                    </a>

                </li>

            </c:forEach>

        </ul>

    </nav>

</div>

<jsp:include page="../common/footer.jsp"/>