<#macro layout>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>MICROGRAM</title>
        <link rel="stylesheet" href="/css/style.css/">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>

    <header>
        <nav class="navbar navbar-expand-md bg-dark " data-bs-theme="dark">
            <div class="container">

                <a class="navbar-brand" href="/"><img src="/logo.png/" width="120px" alt="Microgram"></a>
                <button class="navbar-toggler text-light bg-orange btn-user ms-auto" type="button"
                        data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <i class="bi bi-list-nested"></i>
                </button>

                <button type="button"
                        class="btn btn-link btn-user bg-orange rounded-1 text-light bg-orange order-md-1 ms-3"
                        onclick="window.location.href='/user'"
                        data-bs-toggle="tooltip" data-bs-placement="left" title="Profile">
                    <i class="bi bi-person-circle"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav fw-medium mx-auto">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/">Лента</a>
                        </li>
                        <li class="mx-3 nav-item">
                            <a class="nav-link " href="#">Поиск</a>
                        </li>
                        <li class="mx-3 nav-item">
                            <a class="nav-link" href="/register">Зарегистрироваться</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>


    <body>


    <div class="container">
        <#nested >
    </div>


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
</#macro>