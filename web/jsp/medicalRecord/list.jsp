<%-- 
    Document   : list
    Created on : Jul 2, 2026, 5:22:41 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="d-flex justify-content-between mb-3">

        <h2>Medical Record Management</h2>

        <a href="medicalRecord?action=add"
           class="btn btn-success">
            Add Medical Record
        </a>

    </div>

    <form action="medicalRecord"
          method="get"
          class="row g-2 mb-3">

        <input type="hidden" name="action" value="search">

        <div class="col-auto">
            <input type="text"
                   name="keyword"
                   class="form-control"
                   placeholder="Search by patient name"
                   value="${keyword}">
        </div>

        <div class="col-auto">
            <button class="btn btn-primary" type="submit">
                <i class="fa fa-magnifying-glass"></i>
                Search
            </button>
        </div>

        <c:if test="${not empty keyword}">
            <div class="col-auto">
                <a href="medicalRecord?action=list"
                   class="btn btn-secondary">
                    Clear
                </a>
            </div>
        </c:if>

    </form>

    <table class="table table-bordered table-hover">

        <thead class="table-dark">

            <tr>
                <th>Record ID</th>
                <th>Patient ID</th>
                <th>Doctor ID</th>
                <th>Diagnosis</th>
                <th>Treatment</th>
                <th>Created Date</th>
                <th width="180">Action</th>
            </tr>

        </thead>

        <tbody>

            <c:forEach items="${listRecord}"
                       var="r">

                <tr>

                    <td>${r.recordID}</td>
                    <td>${r.patientID}</td>
                    <td>${r.doctorID}</td>
                    <td>${r.diagnosis}</td>
                    <td>${r.treatment}</td>
                    <td>${r.createdDate}</td>

                    <td>

                        <a href="medicalRecord?action=edit&id=${r.recordID}"
                           class="btn btn-warning btn-sm">
                            Edit
                        </a>

                        <form action="medicalRecord"
                              method="post"
                              style="display:inline">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="id"
                                   value="${r.recordID}">

                            <button class="btn btn-danger btn-sm"
                                    onclick="return confirm('Delete this medical record?')">
                                Delete
                            </button>

                        </form>

                    </td>

                </tr>

            </c:forEach>

            <c:if test="${empty listRecord}">
                <tr>
                    <td colspan="7" class="text-center text-muted">
                        No medical records found.
                    </td>
                </tr>
            </c:if>

        </tbody>

    </table>

    <c:if test="${empty keyword}">

        <nav>

            <ul class="pagination">

                <c:forEach begin="1"
                           end="${endPage}"
                           var="i">

                    <li class="page-item
                        ${currentPage==i?'active':''}">

                        <a class="page-link"
                           href="medicalRecord?action=list&page=${i}">
                            ${i}
                        </a>

                    </li>

                </c:forEach>

            </ul>

        </nav>

    </c:if>

</div>

<jsp:include page="../common/footer.jsp"/>
