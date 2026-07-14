<%-- 
    Document   : form
    Created on : Jul 2, 2026, 5:08:07 PM
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

                ${doctor == null || doctor.doctorID == 0 ?
                'Add Doctor'
                :
                'Update Doctor'}

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

            <form action="doctor"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="${(doctor==null || doctor.doctorID == 0)?'insert':'update'}">

                <c:if test="${doctor != null and doctor.doctorID != 0}">

                    <input type="hidden"
                           name="doctorID"
                           value="${doctor.doctorID}">

                </c:if>

                <div class="mb-3">

                    <label>Full Name</label>

                    <input type="text"
                           name="fullName"
                           class="form-control"
                           value="${doctor.fullName}"
                           required>

                </div>

                <div class="mb-3">

                    <label>Specialization</label>

                    <input type="text"
                           name="specialization"
                           class="form-control"
                           value="${doctor.specialization}">

                </div>

                <div class="mb-3">

                    <label>Phone</label>

                    <input type="tel"
                           name="phone"
                           class="form-control"
                           pattern="0\d{9}"
                           title="Phone must be 10 digits, starting with 0"
                           value="${doctor.phone}"
                           required>

                </div>

                <div class="mb-3">

                    <label>Department ID</label>

                    <input type="number"
                           name="departmentID"
                           class="form-control"
                           value="${doctor.departmentID}"
                           required>

                </div>

                <button class="btn btn-primary">

                    Save

                </button>

                <a href="doctor?action=list"
                   class="btn btn-secondary">

                    Back

                </a>

            </form>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>