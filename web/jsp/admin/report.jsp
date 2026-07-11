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

<div class="content">

    <h2 class="mb-4">Reports &amp; Statistics</h2>

    <div class="row">

        <!-- 1. Appointment by Status -->
        <div class="col-md-6 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-calendar-check"></i>
                    Appointments by Status
                </h5>

                <table class="table table-striped table-sm">

                    <thead>
                        <tr>
                            <th>Status</th>
                            <th>Total</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${STATUS_REPORT}" var="row">
                            <tr>
                                <td>${row[0]}</td>
                                <td>${row[1]}</td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty STATUS_REPORT}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">
                                    No data.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

        <!-- 2. Top 5 Doctors -->
        <div class="col-md-6 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-user-doctor"></i>
                    Top 5 Doctors (by appointments)
                </h5>

                <table class="table table-striped table-sm">

                    <thead>
                        <tr>
                            <th>Doctor</th>
                            <th>Total Appointments</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${TOP_DOCTOR}" var="row">
                            <tr>
                                <td>${row[0]}</td>
                                <td>${row[1]}</td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty TOP_DOCTOR}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">
                                    No data.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

        <!-- 3. Appointments by Department -->
        <div class="col-md-6 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-building"></i>
                    Appointments by Department
                </h5>

                <table class="table table-striped table-sm">

                    <thead>
                        <tr>
                            <th>Department</th>
                            <th>Total</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${DEPARTMENT_REPORT}" var="row">
                            <tr>
                                <td>${row[0]}</td>
                                <td>${row[1]}</td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty DEPARTMENT_REPORT}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">
                                    No data.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

        <!-- 4. Revenue by Month -->
        <div class="col-md-6 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-sack-dollar"></i>
                    Revenue by Month
                </h5>

                <table class="table table-striped table-sm">

                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Revenue</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${REVENUE_REPORT}" var="row">
                            <tr>
                                <td>${row[0]}</td>
                                <td>
                                    <fmt:formatNumber value="${row[1]}"
                                                       type="number"
                                                       groupingUsed="true"/>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty REVENUE_REPORT}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">
                                    No data.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

        <!-- 5. Medical Records by Month -->
        <div class="col-md-6 mb-4">

            <div class="card-box">

                <h5 class="mb-3">
                    <i class="fa fa-file-medical"></i>
                    Medical Records Created by Month
                </h5>

                <table class="table table-striped table-sm">

                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Total</th>
                        </tr>
                    </thead>

                    <tbody>

                        <c:forEach items="${RECORD_REPORT}" var="row">
                            <tr>
                                <td>${row[0]}</td>
                                <td>${row[1]}</td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty RECORD_REPORT}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">
                                    No data.
                                </td>
                            </tr>
                        </c:if>

                    </tbody>

                </table>

            </div>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>
