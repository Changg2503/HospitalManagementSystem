<%-- 
    Document   : login
    Created on : Jun 29, 2026, 3:39:20 PM
    Author     : Nguyen Trang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Hospital Management System</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css"
              rel="stylesheet">

        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">

        <style>

            body{
                background: linear-gradient(
                    135deg,
                    #74ebd5,
                    #ACB6E5);

                height:100vh;

                display:flex;
                justify-content:center;
                align-items:center;
            }

            .login-card{
                width:420px;
                background:white;
                padding:40px;
                border-radius:20px;
                box-shadow:0 10px 25px rgba(0,0,0,0.2);
            }

            .hospital-icon{
                font-size:70px;
                color:#0d6efd;
            }

            .title{
                font-weight:bold;
                color:#0d6efd;
            }

        </style>

    </head>

    <body>

        <div class="login-card">

            <div class="text-center mb-4">

                <i class="fa-solid fa-hospital hospital-icon"></i>
                <img src="images/hospital.png">

                <h2 class="title mt-3">
                    Hospital Management
                </h2>

                <p class="text-muted">
                    Login to continue
                </p>

            </div>

            <form action="login"
                  method="post">

                <div class="mb-3">

                    <label class="form-label">
                        Username
                    </label>

                    <input type="text"
                           name="username"
                           class="form-control"
                           required>

                </div>

                <div class="mb-3">

                    <label class="form-label">
                        Password
                    </label>

                    <input type="password"
                           name="password"
                           class="form-control"
                           required>

                </div>

                <div class="text-danger text-center mb-3">

                    ${ERROR}

                </div>

                <button class="btn btn-primary w-100">

                    <i class="fa-solid fa-right-to-bracket"></i>
                    Login

                </button>

            </form>

            <hr>

            <div class="text-center">

                <span>
                    Don't have an account?
                </span>

                <a href="register">
                    Register
                </a>

            </div>

        </div>

    </body>
</html>