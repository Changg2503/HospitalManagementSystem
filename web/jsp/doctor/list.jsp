<%-- 
    Document   : list
    Created on : Jul 2, 2026, 5:07:56 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="d-flex justify-content-between mb-3">
        <h2>Doctor Management</h2>

        <a href="doctor?action=add"
           class="btn btn-success">
            Add Doctor
        </a>
    </div>

    <form action="doctor"
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

    <table class="table table-bordered table-hover">

        <thead class="table-dark">

        <tr>
            <th>ID</th>
            <th>Full Name</th>
            <th>Specialization</th>
            <th>Phone</th>
            <th>Department ID</th>
            <th width="180">Action</th>
        </tr>

        </thead>

        <tbody>

        <c:forEach items="${listDoctor}"
                   var="d">

            <tr>

                <td>${d.doctorID}</td>
                <td>${d.fullName}</td>
                <td>${d.specialization}</td>
                <td>${d.phone}</td>
                <td>${d.departmentID}</td>

                <td>

                    <a href="doctor?action=edit&id=${d.doctorID}"
                       class="btn btn-warning btn-sm">
                        Edit
                    </a>

                    <form action="doctor"
                          method="post"
                          style="display:inline">

                        <input type="hidden"
                               name="action"
                               value="delete">

                        <input type="hidden"
                               name="id"
                               value="${d.doctorID}">

                        <button class="btn btn-danger btn-sm"
                                onclick="return confirm('Delete this doctor?')">

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
                       href="doctor?action=list&page=${i}">
                        ${i}
                    </a>

                </li>

            </c:forEach>

        </ul>

    </nav>

</div>

<jsp:include page="../common/footer.jsp"/>