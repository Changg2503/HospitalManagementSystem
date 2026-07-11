<%-- 
    Document   : form
    Created on : Jul 2, 2026, 5:21:17 PM
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
                ${d == null ?
                'Add Department'
                :
                'Update Department'}
            </h3>
        </div>

        <div class="card-body">

            <form action="department"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="${d==null?'insert':'update'}">

                <c:if test="${d != null}">
                    <input type="hidden"
                           name="id"
                           value="${d.departmentID}">
                </c:if>

                <div class="mb-3">

                    <label>Department Name</label>

                    <input type="text"
                           name="name"
                           class="form-control"
                           value="${d.departmentName}"
                           required>

                </div>

                <button class="btn btn-primary">
                    Save
                </button>

                <a href="department?action=list"
                   class="btn btn-secondary">
                    Back
                </a>

            </form>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>