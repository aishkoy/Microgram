<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="../css/login.css">
</head>
<body>

<section class="vh-100 mt-5">

    <div class="container-fluid h-custom mt-5 pt-5">
        <div class="row d-flex justify-content-center align-items-center h-100 pt-5">
            <div class="col-md-9 col-lg-6 col-xl-5 ">
                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                     class="img-fluid" alt="Sample image">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <form action="/login" method="post">
                    <div data-mdb-input-init class="form-outline mb-4">
                        <input type="email" id="username" class="form-control form-control-lg"
                               placeholder="Enter email address or username" name="username"/>
                        <label class="form-label" for="username">Email/Username</label>
                    </div>

                    <div data-mdb-input-init class="form-outline mb-3">
                        <input type="password" id="password" class="form-control form-control-lg"
                               placeholder="Enter password" name="password"/>
                        <label class="form-label" for="password">Password</label>
                    </div>
                    <div  class="form-outline mb-3">
                        <input class="form-check-input" type="checkbox" id="remember-me"
                               name="remember-me"/>
                        <label class="form-check-label" for="remember-me">Remember me?</label>
                    </div>


                    <div class="text-center text-lg-start mt-4 pt-2">
                        <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-lg"
                                style="padding-left: 2.5rem; padding-right: 2.5rem;">Login
                        </button>
                        <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account?
                            <a href="/register" class="link-danger">Register</a>
                        </p>
                    </div>

                </form>
            </div>
        </div>
    </div>


    <div class="d-flex flex-column flex-md-row text-center text-md-start justify-content-between py-4 px-4 px-xl-5 bg-primary footer">
        <div class="text-white mb-3 mb-md-0">
            Copyright © 2024. All rights reserved. By DAD.
        </div>
    </div>

</section>
<style>
    .footer {
        position: fixed;
        left: 0;
        bottom: 0;
        width: 100%;
        text-align: center;
    }
</style>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>

