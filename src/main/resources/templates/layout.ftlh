<#import "/spring.ftl" as spring>

<#assign known = SPRING_SECURITY_CONTEXT?? >
<#if known>
    <#assign
    currentUserId = SPRING_SECURITY_CONTEXT.authentication.principal.getUserId()
    >
</#if>

<#macro page title="" styles="" activeNav="">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="_csrf" content="${_csrf.token}">
        <meta name="_csrf_header" content="${_csrf.headerName}">
        <title>Instagram</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #fafafa;
            }

            .sidebar {
                position: fixed;
                top: 0;
                bottom: 0;
                left: 0;
                width: 240px;
                background: #fff;
                border-right: 1px solid #ddd;
                padding: 24px 16px;
                box-shadow: 2px 0 6px rgba(0, 0, 0, 0.05);
                z-index: 1000;
            }

            .main-content {
                margin-left: 240px;
                padding: 32px;
                min-height: 100vh;
                background-color: #f9f9f9;
            }

            .nav-link {
                display: flex;
                align-items: center;
                color: #333;
                padding: 10px 12px;
                border-radius: 8px;
                transition: background 0.2s ease;
                font-size: 16px;
                margin-bottom: 8px;
                text-decoration: none;
            }

            .nav-link:hover {
                background-color: #f0f0f0;
            }

            .nav-link.active {
                background-color: #e9ecef;
                font-weight: 600;
            }

            .profile-stats div {
                text-align: center;
            }

            .nav-link i {
                margin-right: 8px;
            }

        </style>
    </head>
    <body>
    <div class="sidebar">
        <h4 class="mb-4">Instagram</h4>

        <nav class="nav flex-column">
            <a class="nav-link" href="/"><i class="bi bi-house-door"></i> Главная</a>
            <a class="nav-link" href="/search"><i class="bi bi-search"></i> Поисковый запрос</a>
            <a class="nav-link" href="/posts"><i class="bi bi-compass"></i> Интересное</a>
            <a class="nav-link" href="/posts/create"><i class="bi bi-plus-square"></i> Создать</a>

            <#if known>
                <a class="nav-link" href="/users/${currentUserId}"><i class="bi bi-person"></i> Профиль</a>
                <#if  activeNav == "profile" && _csrf??>
                    <form action="/logout" method="post" class="d-inline">
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                        <button type="submit" class="nav-link"><i class="bi bi-box-arrow-right"></i> Выйти</button>
                    </form>
                </#if>
            <#else>
                <a class="nav-link <#if activeNav == "login">active</#if>" href="auth/login"><i
                            class="bi bi-box-arrow-in-right"></i> Войти</a>
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