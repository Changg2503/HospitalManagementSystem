<%-- 
    Document   : form
    Created on : Jul 2, 2026, 5:22:51 PM
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
                ${record == null ?
                'Add Medical Record'
                :
                'Update Medical Record'}
            </h3>

        </div>

        <div class="card-body">

            <form action="medicalRecord"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="${record==null?'insert':'update'}">

                <c:if test="${record != null}">
                    <input type="hidden"
                           name="recordID"
                           value="${record.recordID}">
                </c:if>

                <div class="row">

                    <div class="col-md-6 mb-3">

                        <label>Patient ID</label>

                        <input type="number"
                               name="patientID"
                               class="form-control"
                               value="${record.patientID}"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Doctor ID</label>

                        <input type="number"
                               name="doctorID"
                               class="form-control"
                               value="${record.doctorID}"
                               required>

                    </div>

                    <div class="col-md-12 mb-3">

                        <label>Diagnosis</label>

                        <textarea name="diagnosis"
                                  class="form-control"
                                  rows="3"
                                  required>${record.diagnosis}</textarea>

                    </div>

                    <div class="col-md-12 mb-3">

                        <label>Treatment</label>

                        <textarea name="treatment"
                                  class="form-control"
                                  rows="3">${record.treatment}</textarea>

                    </div>

                    <c:if test="${record != null}">
                        <div class="col-md-6 mb-3">

                            <label>Created Date</label>

                            <input type="text"
                                   class="form-control"
                                   value="${record.createdDate}"
                                   disabled>

                        </div>
                    </c:if>

                </div>

                <button class="btn btn-primary">
                    Save
                </button>

                <a href="medicalRecord?action=list"
                   class="btn btn-secondary">
                    Back
                </a>

            </form>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>
