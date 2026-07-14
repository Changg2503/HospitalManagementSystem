<%-- 
    Document   : report
    Created on : Jul 2, 2026, 5:24:37 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt"
          uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>

<style>
    .chart-box{
        position: relative;
        height: 280px;
    }
</style>

<div class="content">

    <h2 class="mb-4">Reports &amp; Statistics Dashboard</h2>

    <div class="row">

        <!-- 1. Appointment by Status -->
        <div class="col-md-6 mb-4">
            <div class="card-box">
                <h5 class="mb-3">
                    <i class="fa fa-calendar-check"></i>
                    Appointments by Status
                </h5>

                <c:if test="${empty STATUS_REPORT}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty STATUS_REPORT}">
                    <div class="chart-box">
                        <canvas id="statusChart"></canvas>
                    </div>
                    <!-- dữ liệu ẩn, JS sẽ đọc từ đây (tránh lỗi escape ký tự đặc biệt) -->
                    <table id="dataStatus" style="display:none">
                        <c:forEach items="${STATUS_REPORT}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

        <!-- 2. Top 5 Doctors -->
        <div class="col-md-6 mb-4">
            <div class="card-box">
                <h5 class="mb-3">
                    <i class="fa fa-user-doctor"></i>
                    Top 5 Doctors (by appointments)
                </h5>

                <c:if test="${empty TOP_DOCTOR}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty TOP_DOCTOR}">
                    <div class="chart-box">
                        <canvas id="doctorChart"></canvas>
                    </div>
                    <table id="dataDoctor" style="display:none">
                        <c:forEach items="${TOP_DOCTOR}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

        <!-- 3. Appointments by Department -->
        <div class="col-md-6 mb-4">
            <div class="card-box">
                <h5 class="mb-3">
                    <i class="fa fa-building"></i>
                    Appointments by Department
                </h5>

                <c:if test="${empty DEPARTMENT_REPORT}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty DEPARTMENT_REPORT}">
                    <div class="chart-box">
                        <canvas id="departmentChart"></canvas>
                    </div>
                    <table id="dataDepartment" style="display:none">
                        <c:forEach items="${DEPARTMENT_REPORT}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

        <!-- 4. Revenue by Month -->
        <div class="col-md-6 mb-4">
            <div class="card-box">
                <h5 class="mb-3">
                    <i class="fa fa-sack-dollar"></i>
                    Revenue by Month
                </h5>

                <c:if test="${empty REVENUE_REPORT}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty REVENUE_REPORT}">
                    <div class="chart-box">
                        <canvas id="revenueChart"></canvas>
                    </div>
                    <table id="dataRevenue" style="display:none">
                        <c:forEach items="${REVENUE_REPORT}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

        <!-- 5. Medical Records by Month -->
        <div class="col-md-6 mb-4">
            <div class="card-box">
                <h5 class="mb-3">
                    <i class="fa fa-file-medical"></i>
                    Medical Records Created by Month
                </h5>

                <c:if test="${empty RECORD_REPORT}">
                    <p class="text-muted text-center">No data.</p>
                </c:if>

                <c:if test="${not empty RECORD_REPORT}">
                    <div class="chart-box">
                        <canvas id="recordChart"></canvas>
                    </div>
                    <table id="dataRecord" style="display:none">
                        <c:forEach items="${RECORD_REPORT}" var="row">
                            <tr><td>${row[0]}</td><td>${row[1]}</td></tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>

<script>
    // Đọc dữ liệu an toàn từ bảng ẩn trong DOM (HTML đã tự escape ký tự đặc biệt)
    // thay vì nhúng thẳng chuỗi vào JS -> tránh lỗi vỡ cú pháp khi tên có dấu nháy.
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

    const palette = ['#0d6efd', '#20c997', '#ffc107', '#dc3545', '#6f42c1', '#fd7e14', '#0dcaf0'];

    document.addEventListener('DOMContentLoaded', function () {

        // 1. Status -> Doughnut
        const statusData = readTable('dataStatus');
        if (statusData.labels.length > 0) {
            new Chart(document.getElementById('statusChart'), {
                type: 'doughnut',
                data: {
                    labels: statusData.labels,
                    datasets: [{data: statusData.data, backgroundColor: palette}]
                },
                options: {maintainAspectRatio: false, plugins: {legend: {position: 'bottom'}}}
            });
        }

        // 2. Top Doctors -> Horizontal bar
        const doctorData = readTable('dataDoctor');
        if (doctorData.labels.length > 0) {
            new Chart(document.getElementById('doctorChart'), {
                type: 'bar',
                data: {
                    labels: doctorData.labels,
                    datasets: [{label: 'Appointments', data: doctorData.data, backgroundColor: '#0d6efd'}]
                },
                options: {
                    indexAxis: 'y',
                    maintainAspectRatio: false,
                    plugins: {legend: {display: false}},
                    scales: {x: {beginAtZero: true, ticks: {stepSize: 1}}}
                }
            });
        }

        // 3. Department -> Pie
        const deptData = readTable('dataDepartment');
        if (deptData.labels.length > 0) {
            new Chart(document.getElementById('departmentChart'), {
                type: 'pie',
                data: {
                    labels: deptData.labels,
                    datasets: [{data: deptData.data, backgroundColor: palette}]
                },
                options: {maintainAspectRatio: false, plugins: {legend: {position: 'bottom'}}}
            });
        }

        // 4. Revenue -> Line
        const revData = readTable('dataRevenue');
        if (revData.labels.length > 0) {
            new Chart(document.getElementById('revenueChart'), {
                type: 'line',
                data: {
                    labels: revData.labels,
                    datasets: [{
                        label: 'Revenue',
                        data: revData.data,
                        borderColor: '#20c997',
                        backgroundColor: 'rgba(32,201,151,0.2)',
                        fill: true,
                        tension: 0.3
                    }]
                },
                options: {maintainAspectRatio: false, plugins: {legend: {display: false}}, scales: {y: {beginAtZero: true}}}
            });
        }

        // 5. Medical Records -> Bar
        const recData = readTable('dataRecord');
        if (recData.labels.length > 0) {
            new Chart(document.getElementById('recordChart'), {
                type: 'bar',
                data: {
                    labels: recData.labels,
                    datasets: [{label: 'Medical Records', data: recData.data, backgroundColor: '#6f42c1'}]
                },
                options: {
                    maintainAspectRatio: false,
                    plugins: {legend: {display: false}},
                    scales: {y: {beginAtZero: true, ticks: {stepSize: 1}}}
                }
            });
        }
    });
</script>
