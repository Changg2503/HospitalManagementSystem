<%-- 
    Document   : form
    Created on : Jul 2, 2026, 5:04:35 PM
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

                ${patient == null ?
                'Add Patient'
                :
                'Update Patient'}

            </h3>

        </div>

        <div class="card-body">

            <form action="patient"
                  method="post">

                <input type="hidden"
                       name="action"
                       value="${patient==null?'insert':'update'}">

                <c:if test="${patient != null}">

                    <input type="hidden"
                           name="patientID"
                           value="${patient.patientID}">

                </c:if>

                <div class="row">

                    <div class="col-md-6 mb-3">

                        <label>Account ID</label>

                        <input type="number"
                               name="accountID"
                               class="form-control"
                               value="${patient.accountID}"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Full Name</label>

                        <input type="text"
                               name="fullName"
                               class="form-control"
                               value="${patient.fullName}"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Gender</label>

                        <select name="gender"
                                class="form-select">

                            <option value="Male"
                                    ${patient.gender=='Male'?'selected':''}>
                                Male
                            </option>

                            <option value="Female"
                                    ${patient.gender=='Female'?'selected':''}>
                                Female
                            </option>

                        </select>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Date of Birth</label>

                        <input type="date"
                               name="dob"
                               class="form-control"
                               value="${patient.dob}">

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Phone</label>

                        <input type="text"
                               name="phone"
                               class="form-control"
                               value="${patient.phone}">

                    </div>

                    <div class="col-md-6 mb-3">

                        <label>Email</label>

                        <input type="email"
                               name="email"
                               class="form-control"
                               value="${patient.email}">

                    </div>

                    <div class="col-md-12 mb-3">

                        <label>Address</label>

                        <textarea name="address"
                                  class="form-control"
                                  rows="3">${patient.address}</textarea>

                    </div>

                </div>

                <button class="btn btn-primary">

                    Save

                </button>

                <a href="patient?action=list"
                   class="btn btn-secondary">

                    Back

                </a>

            </form>

        </div>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>