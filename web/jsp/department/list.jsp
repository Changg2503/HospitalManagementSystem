<%-- 
    Document   : list
    Created on : Jul 2, 2026, 5:21:12 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"
          uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <div class="d-flex justify-content-between mb-3">

        <h2>Department Management</h2>

        <a href="department?action=add"
           class="btn btn-success">
            Add Department
        </a>

    </div>

    <form action="department"
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
                   placeholder="Search department">

            <button class="btn btn-primary">
                Search
            </button>

        </div>

    </form>

    <table class="table table-bordered table-hover">

        <thead class="table-dark">

        <tr>
            <th>ID</th>
            <th>Department Name</th>
            <th width="180">Action</th>
        </tr>

        </thead>

        <tbody>

        <c:forEach items="${list}"
                   var="d">

            <tr>

                <td>${d.departmentID}</td>
                <td>${d.departmentName}</td>

                <td>

                    <a href="department?action=edit&id=${d.departmentID}"
                       class="btn btn-warning btn-sm">
                        Edit
                    </a>

                    <form action="department"
                          method="post"
                          style="display:inline">

                        <input type="hidden"
                               name="action"
                               value="delete">

                        <input type="hidden"
                               name="id"
                               value="${d.departmentID}">

                        <button class="btn btn-danger btn-sm"
                                onclick="return confirm('Delete this department?')">

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
                       href="department?action=list&page=${i}">
                        ${i}
                    </a>

                </li>

            </c:forEach>

        </ul>

    </nav>

</div>

<jsp:include page="../common/footer.jsp"/>