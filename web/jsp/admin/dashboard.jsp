<%-- 
    Document   : dashboard
    Created on : Jun 29, 2026, 3:57:26 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>

<div class="content">

    <h2 class="mb-1">Dashboard</h2>
    <p class="text-muted mb-4">
        Welcome back, <strong>${sessionScope.ACCOUNT.username}</strong>
        (${sessionScope.ACCOUNT.role})
    </p>

    <div class="row">

        <div class="col-md-3 mb-3">
            <a href="patient?action=list" class="text-decoration-none">
                <div class="card-box">
                    <h5 class="text-muted">
                        <i class="fa fa-user"></i>
                        Total Patients
                    </h5>
                    <h2>${totalPatient}</h2>
                </div>
            </a>
        </div>

        <div class="col-md-3 mb-3">
            <a href="doctor?action=list" class="text-decoration-none">
                <div class="card-box">
                    <h5 class="text-muted">
                        <i class="fa fa-user-doctor"></i>
                        Total Doctors
                    </h5>
                    <h2>${totalDoctor}</h2>
                </div>
            </a>
        </div>

        <div class="col-md-3 mb-3">
            <a href="appointment?action=list" class="text-decoration-none">
                <div class="card-box">
                    <h5 class="text-muted">
                        <i class="fa fa-calendar-check"></i>
                        Total Appointments
                    </h5>
                    <h2>${totalAppointment}</h2>
                </div>
            </a>
        </div>

        <div class="col-md-3 mb-3">
            <a href="medicalRecord?action=list" class="text-decoration-none">
                <div class="card-box">
                    <h5 class="text-muted">
                        <i class="fa fa-file-medical"></i>
                        Total Medical Records
                    </h5>
                    <h2>${totalRecord}</h2>
                </div>
            </a>
        </div>

    </div>

    <div class="row mt-2">

        <!-- Recent appointments -->
        <div class="col-md-7 mb-4">

            <div class="card-box">

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="mb-0">
                        <i class="fa fa-clock-rotate-left"></i>
                        Recent Appointments
                    </h5>
                    <a href="appointment?action=list" class="btn btn-sm btn-outline-primary">
                        View all
                    </a>
                </div>

                <table class="table table-sm table-striped mb-0">

                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Patient ID</th>
                            <th>Doctor ID</th>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${recentAppointments}" var="a">
                            <tr>
                                <td>${a.appointmentID}</td>
                                <td>${a.patientID}</td>
                                <td>${a.doctorID}</td>
                                <td>${a.appointmentDate}</td>
                                <td>
                                    <span class="badge
                                        ${a.status=='Completed' ? 'bg-success' :
                                          a.status=='Cancelled' ? 'bg-danger' :
                                          a.status=='Confirmed' ? 'bg-primary' : 'bg-warning'}">
                                        ${a.status}
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty recentAppointments}">
                            <tr>
                                <td colspan="5" class="text-center text-muted">
                                    No appointments yet.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

        <!-- Mini status chart -->
        <div class="col-md-5 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-chart-pie"></i>
                    Appointment Status Overview
                </h5>

                <c:if test="${empty STATUS_REPORT}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty STATUS_REPORT}">
                    <div style="position:relative;height:220px;">
                        <canvas id="dashboardStatusChart"></canvas>
                    </div>
                    <table id="dataDashStatus" style="display:none">
                        <c:forEach items="${STATUS_REPORT}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>

                <div class="d-grid mt-3">
                    <a href="report" class="btn btn-outline-secondary btn-sm">
                        <i class="fa fa-chart-column"></i>
                        View full reports
                    </a>
                </div>

            </div>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>

<script>
    function readTable(tableId) {
        const labels = [];
        const data = [];
        const table = document.getElementById(tableId);
        if (!table) {
            return {labels: labels, data: data};
        }
        table.querySelectorAll('tr').forEach(function (tr) {
            const cells = tr.querySelectorAll('td');
            if (cells.length === 2) {
                labels.push(cells[0].textContent.trim());
                data.push(parseFloat(cells[1].textContent.trim()) || 0);
            }
        });
        return {labels: labels, data: data};
    }

    document.addEventListener('DOMContentLoaded', function () {

        const dashStatus = readTable('dataDashStatus');

        if (dashStatus.labels.length > 0) {
            new Chart(document.getElementById('dashboardStatusChart'), {
                type: 'doughnut',
                data: {
                    labels: dashStatus.labels,
                    datasets: [{
                        data: dashStatus.data,
                        backgroundColor: ['#0d6efd', '#20c997', '#ffc107', '#dc3545', '#6f42c1']
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    plugins: { legend: { position: 'bottom' } }
                }
            });
        }
    });
</script>
