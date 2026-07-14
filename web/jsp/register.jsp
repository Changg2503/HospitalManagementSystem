<%-- 
    Document   : register
    Created on : Jul 2, 2026, 4:58:44 PM
    Author     : Nguyen Trang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Patient Registration</title>

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

                min-height:100vh;

                display:flex;
                justify-content:center;
                align-items:center;

                padding:30px;
            }

            .register-card{
                width:700px;
                background:white;
                border-radius:20px;
                padding:40px;
                box-shadow:0 10px 25px rgba(0,0,0,0.2);
            }

            .title{
                color:#0d6efd;
                font-weight:bold;
            }

            .hospital-icon{
                font-size:70px;
                color:#0d6efd;
            }

        </style>

    </head>

    <body>

        <div class="register-card">

            <div class="text-center mb-4">

                <i class="fa-solid fa-hospital hospital-icon"></i>

                <h2 class="title mt-3">
                    Patient Registration
                </h2>

                <p class="text-muted">
                    Create your account
                </p>

            </div>

            <form action="register"
                  method="post">

                <div class="row">

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Username
                        </label>

                        <input type="text"
                               name="username"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Password
                        </label>

                        <input type="password"
                               name="password"
                               class="form-control"
                               minlength="6"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Full Name
                        </label>

                        <input type="text"
                               name="fullName"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Gender
                        </label>

                        <select name="gender"
                                class="form-select">

                            <option value="Male">
                                Male
                            </option>

                            <option value="Female">
                                Female
                            </option>

                        </select>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Date of Birth
                        </label>

                        <input type="date"
                               name="dob"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Phone
                        </label>

                        <input type="tel"
                               name="phone"
                               class="form-control"
                               pattern="0\d{9}"
                               title="Phone must be 10 digits, starting with 0"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Email
                        </label>

                        <input type="email"
                               name="email"
                               class="form-control"
                               required>

                    </div>

                    <div class="col-md-6 mb-3">

                        <label class="form-label">
                            Address
                        </label>

                        <input type="text"
                               name="address"
                               class="form-control">

                    </div>

                </div>

                <div class="text-danger text-center mb-3">
                    ${ERROR}
                </div>

                <div class="text-success text-center mb-3">
                    ${SUCCESS}
                </div>

                <button class="btn btn-success w-100">

                    <i class="fa-solid fa-user-plus"></i>
                    Register

                </button>

            </form>

            <hr>

            <div class="text-center">

                Already have an account?

                <a href="login">
                    Login here
                </a>

            </div>

        </div>

    </body>
</html>