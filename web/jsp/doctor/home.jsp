<%-- 
    Document   : home
    Created on : Jun 29, 2026, 4:41:03 PM
    Author     : Nguyen Trang
--%>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/sidebar.jsp"/>

<div class="content">

    <h2>Doctor Dashboard</h2>

    <div class="card-box mt-4">

        <h4>
            Welcome,
            ${sessionScope.account.username}
        </h4>

        <p>
            You are logged in as Doctor.
        </p>

    </div>

</div>

<jsp:include page="../common/footer.jsp"/>