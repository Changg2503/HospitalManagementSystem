<%-- 
    Document   : list
    Created on : Jul 2, 2026, 5:04:18 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="d-flex justify-content-between mb-3">

        <h2>
            <c:if test="${sessionScope.ACCOUNT.role == 'Patient'}">My Profile</c:if>
            <c:if test="${sessionScope.ACCOUNT.role != 'Patient'}">Patient Management</c:if>
        </h2>

        <c:if test="${sessionScope.ACCOUNT.role != 'Patient'}">
            <a href="patient?action=add"
               class="btn btn-success">

                Add Patient

            </a>
        </c:if>

    </div>

    <c:if test="${sessionScope.ACCOUNT.role != 'Patient'}">
    <form action="patient"
          method="get"
          class="mb-3">

        <input type="hidden"
               name="action"
               value="search">

        <div class="input-group">

            <input type="text"
                   name="keyword"
                   value="${keyword}"
                   class="form-control"
                   placeholder="Search by name">

            <button class="btn btn-primary">
                Search
            </button>

        </div>

    </form>
    </c:if>

    <table class="table table-bordered table-hover">

        <thead class="table-dark">

        <tr>
            <th>ID</th>
            <th>Account ID</th>
            <th>Full Name</th>
            <th>Gender</th>
            <th>DOB</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Address</th>
            <th width="180">Action</th>
        </tr>

        </thead>

        <tbody>

        <c:forEach items="${listPatient}"
                   var="p">

            <tr>

                <td>${p.patientID}</td>
                <td>${p.accountID}</td>
                <td>${p.fullName}</td>
                <td>${p.gender}</td>
                <td>${p.dob}</td>
                <td>${p.phone}</td>
                <td>${p.email}</td>
                <td>${p.address}</td>

                <td>

                    <a href="patient?action=edit&id=${p.patientID}"
                       class="btn btn-warning btn-sm">

                        Edit

                    </a>

                    <c:if test="${sessionScope.ACCOUNT.role != 'Patient'}">
                    <form action="patient"
                          method="post"
                          style="display:inline">

                        <input type="hidden"
                               name="action"
                               value="delete">

                        <input type="hidden"
                               name="id"
                               value="${p.patientID}">

                        <button class="btn btn-danger btn-sm"
                                onclick="return confirm('Delete this patient?')">

                            Delete

                        </button>

                    </form>
                    </c:if>

                </td>

            </tr>

        </c:forEach>

        </tbody>

    </table>

    <!-- Pagination -->

    <nav>

        <ul class="pagination">

            <c:forEach begin="1"
                       end="${endPage}"
                       var="i">

                <li class="page-item
                    ${currentPage==i?'active':''}">

                    <a class="page-link"
                       href="patient?action=list&page=${i}">
                        ${i}
                    </a>

                </li>

            </c:forEach>

        </ul>

    </nav>

</div>

<jsp:include page="../common/footer.jsp"/>
