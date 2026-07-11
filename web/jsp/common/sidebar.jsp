<%-- 
    Document   : sidebar
    Created on : Jul 2, 2026, 5:40:04 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="role" value="${sessionScope.ACCOUNT.role}"/>

<div class="sidebar">

    <h3>Hospital System</h3>

    <%-- Dashboard: Admin only --%>
    <c:if test="${role == 'Admin'}">
        <a href="${pageContext.request.contextPath}/dashboard">
            <i class="fa fa-home"></i>
            Dashboard
        </a>
    </c:if>

    <%-- Patient management: Patient (own record) + Admin --%>
    <c:if test="${role == 'Patient' || role == 'Admin'}">
        <a href="${pageContext.request.contextPath}/patient?action=list">
            <i class="fa fa-user"></i>
            Patient
        </a>
    </c:if>

    <%-- Doctor management: Doctor (own profile) + Admin --%>
    <c:if test="${role == 'Doctor' || role == 'Admin'}">
        <a href="${pageContext.request.contextPath}/doctor?action=list">
            <i class="fa fa-user-doctor"></i>
            Doctor
        </a>
    </c:if>

    <%-- Department: Admin only --%>
    <c:if test="${role == 'Admin'}">
        <a href="${pageContext.request.contextPath}/department?action=list">
            <i class="fa fa-building"></i>
            Department
        </a>
    </c:if>

    <%-- Appointment: everyone logged in --%>
    <c:if test="${not empty role}">
        <a href="${pageContext.request.contextPath}/appointment?action=list">
            <i class="fa fa-calendar"></i>
            Appointment
        </a>
    </c:if>

    <%-- Medical Record: everyone logged in (Patient = read only, enforced by filter) --%>
    <c:if test="${not empty role}">
        <a href="${pageContext.request.contextPath}/medicalRecord?action=list">
            <i class="fa fa-file-medical"></i>
            Medical Record
        </a>
    </c:if>

    <%-- Report: Admin only --%>
    <c:if test="${role == 'Admin'}">
        <a href="${pageContext.request.contextPath}/report">
            <i class="fa fa-chart-column"></i>
            Report
        </a>
    </c:if>

    <a href="${pageContext.request.contextPath}/logout">
        <i class="fa fa-right-from-bracket"></i>
        Logout
    </a>

</div>
