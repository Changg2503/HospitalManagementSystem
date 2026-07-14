<%-- 
    Document   : header
    Created on : Jul 2, 2026, 5:39:25 PM
    Author     : Nguyen Trang

<jsp:include
        page="../common/header.jsp"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css"
      rel="stylesheet">

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">

<style>

body{
    background:#f4f6f9;
}

.content{
    margin-left:260px;
    padding:30px;
}

.sidebar{
    width:250px;
    height:100vh;
    background:#343a40;
    position:fixed;
    left:0;
    top:0;
}

.sidebar h3{
    color:white;
    text-align:center;
    padding:20px;
}

.sidebar a{
    display:block;
    color:white;
    padding:12px 20px;
    text-decoration:none;
}

.sidebar a:hover{
    background:#495057;
}

.card-box{
    background:white;
    border-radius:10px;
    padding:20px;
    box-shadow:0 0 10px rgba(0,0,0,0.1);
}
</style>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- ============================================
     FLASH MESSAGE (thông báo thành công / lỗi)
     Servlet set session attribute FLASH_SUCCESS / FLASH_ERROR
     trước khi sendRedirect; hiển thị 1 lần rồi tự xóa.
     ============================================ --%>

<div class="toast-container position-fixed top-0 end-0 p-3" style="z-index:1080">

    <c:if test="${not empty sessionScope.FLASH_SUCCESS}">
        <div id="flashToastSuccess"
             class="toast align-items-center text-bg-success border-0"
             role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fa fa-circle-check"></i>
                    ${sessionScope.FLASH_SUCCESS}
                </div>
                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast"></button>
            </div>
        </div>
        <% session.removeAttribute("FLASH_SUCCESS"); %>
    </c:if>

    <c:if test="${not empty sessionScope.FLASH_ERROR}">
        <div id="flashToastError"
             class="toast align-items-center text-bg-danger border-0"
             role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="fa fa-circle-exclamation"></i>
                    ${sessionScope.FLASH_ERROR}
                </div>
                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast"></button>
            </div>
        </div>
        <% session.removeAttribute("FLASH_ERROR"); %>
    </c:if>

</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.toast').forEach(function (el) {
            new bootstrap.Toast(el, {delay: 4000}).show();
        });
    });
</script>