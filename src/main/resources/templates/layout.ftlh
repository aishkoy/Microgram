<#macro page title="" styles="" activeNav="">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Instagram</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #fafafa;
            }
            .sidebar {
                position: fixed;
                left: 0;
                top: 0;
                bottom: 0;
                width: 250px;
                background-color: white;
                border-right: 1px solid #dbdbdb;
                padding: 20px;
            }
            .main-content {
                margin-left: 250px;
                padding: 20px;
            }
            .nav-link {
                color: #262626;
                margin-bottom: 10px;
                font-size: 16px;
            }
            .nav-link.active {
                font-weight: bold;
            }
            .profile-stats div {
                text-align: center;
            }
        </style>
    </head>
    <body>
    <div class="sidebar">
        <h4 class="mb-4">Instagram</h4>

        <nav class="nav flex-column">
            <a class="nav-link" href="#"><i class="bi bi-house-door"></i> Главная</a>
            <a class="nav-link" href="#"><i class="bi bi-search"></i> Поисковый запрос</a>
            <a class="nav-link" href="#"><i class="bi bi-compass"></i> Интересное</a>
            <a class="nav-link" href="#"><i class="bi bi-plus-square"></i> Создать</a>

            <#if Session.SPRING_SECURITY_CONTEXT??>
                <a class="nav-link" href="/profile"><i class="bi bi-person"></i> Профиль</a>
                <#if  activeNav == "profile" && _csrf??>
                    <form action="/logout" method="post" class="d-inline">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
                        <button type="submit" class="nav-link"><i class="bi bi-box-arrow-right"></i> Выйти</button>
                    </form>
                </#if>
            <#else>
                <a class="nav-link <#if activeNav == "login">active</#if>" href="/login"><i class="bi bi-box-arrow-in-right"></i> Войти</a>
            </#if>
        </nav>
    </div>

    <div class="main-content">
        <#nested>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    </body>
    </html>
</#macro>