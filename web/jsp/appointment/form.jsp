<%-- 
    Document   : form
    Created on : Jul 2, 2026, 5:19:22 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="card">

        <div class="card-header">

            <h3>
                ${appointment == null || appointment.appointmentID == 0 ?
                'Add Appointment'
                :
                'Update Appointment'}
            </h3>

        </div>

        <div class="card-body">

            <c:if test="${not empty errors}">
                <div class="alert alert-danger">
                    <ul class="mb-0">
                        <c:forEach items="${errors}" var="err">
                            <li>${err}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="appointment"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="${(appointment==null || appointment.appointmentID == 0)?'insert':'update'}">

                <c:if test="${appointment != null and appointment.appointmentID != 0}">
                    <input type="hidden"
                           name="appointmentID"
                           value="${appointment.appointmentID}">
                </c:if>

                <div class="mb-3">

                    <label>Patient ID</label>

                    <input type="number"
                           name="patientID"
                           class="form-control"
                           value="${appointment.patientID}"
                           required>

                </div>

                <div class="mb-3">

                    <label>Doctor ID</label>

                    <input type="number"
                           name="doctorID"
                           class="form-control"
                           value="${appointment.doctorID}"
                           required>

                </div>

                <div class="mb-3">

                    <label>Appointment Date</label>

                    <input type="datetime-local"
                           name="appointmentDate"
                           class="form-control"
                           value="${appointment.appointmentDate}"
                           required>

                </div>

                <div class="mb-3">

                    <label>Status</label>

                    <select name="status"
                            class="form-select">

                        <option value="Pending"
                            ${appointment.status=='Pending'?'selected':''}>
                            Pending
                        </option>

                        <option value="Confirmed"
                            ${appointment.status=='Confirmed'?'selected':''}>
                            Confirmed
                        </option>

                        <option value="Completed"
                            ${appointment.status=='Completed'?'selected':''}>
                            Completed
                        </option>

                        <option value="Cancelled"
                            ${appointment.status=='Cancelled'?'selected':''}>
                            Cancelled
                        </option>

                    </select>

                </div>

                <button class="btn btn-primary">
                    Save
                </button>

                <a href="appointment?action=list"
                   class="btn btn-secondary">
                    Back
                </a>

            </form>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>