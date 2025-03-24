<#macro layout>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Suslike</title>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <script src="https://unpkg.com/lucide@latest"></script>
        <style>
            .nav-icon {
                width: 28px;
                height: 28px;
                stroke-width: 1.5;
                color: #6b7280;
                transition: color 0.2s, transform 0.2s;
            }

            .nav-icon:hover, .active-nav .nav-icon {
                color: #111827;
                transform: scale(1.1);
            }

            .content-with-sidebar {
                margin-left: 80px;
                width: calc(100% - 80px);
            }

            @media (max-width: 640px) {
                .nav-sidebar {
                    width: 100%;
                    height: auto;
                    position: fixed;
                    bottom: 0;
                    left: 0;
                    z-index: 50;
                }

                .nav-sidebar ul {
                    flex-direction: row;
                    justify-content: space-around;
                    width: 100%;
                    padding: 0.5rem;
                }

                .content-with-sidebar {
                    margin-left: 0;
                    width: 100%;
                    padding-bottom: 60px;
                }
            }
        </style>
    </head>
    <body class="bg-gray-50 min-h-screen">
    <#-- Check if user is logged in -->
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign username = SPRING_SECURITY_CONTEXT.authentication.name>
        <#assign myProfileUrl = "/user">
    <#else>
        <#assign myProfileUrl = "/login">
    </#if>


    <!-- Navigation Sidebar -->
    <nav class="nav-sidebar w-20 h-screen bg-white shadow-sm fixed left-0 top-0 flex flex-col items-center justify-center py-6 border-r border-gray-200">
        <ul class="flex flex-col gap-8">
            <li>
                <a href="/" >
                    <i data-lucide="home" class="nav-icon"></i>
                </a>
            </li>
            <li>
                <a href="user/search">
                    <i data-lucide="search" class="nav-icon"></i>
                </a>
            </li>
            <li>
                <a href="/post">
                    <i data-lucide="plus-circle" class="nav-icon"></i>
                </a>
            </li>
            <li>
                <a href="/favorites">
                    <i data-lucide="heart" class="nav-icon"></i>
                </a>
            </li>
            <li>
                <a href="${myProfileUrl}">
                    <i data-lucide="user" class="nav-icon"></i>
                </a>
            </li>
        </ul>
    </nav>

    <!-- Main Content -->
    <div class="content-with-sidebar">
        <main class="container mx-auto px-4 py-5">
            <#nested>
        </main>
    </div>

    <script>
        // Initialize Lucide icons
        lucide.createIcons();

        // Additional JavaScript for active link detection in case server-side detection fails
        document.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const navLinks = document.querySelectorAll('nav a');

            navLinks.forEach(link => {
                const href = link.getAttribute('href');
                if (currentPath === href ||
                    (href !== '/' && currentPath.startsWith(href))) {
                    link.classList.add('active-nav');
                }
            });
        });
    </script>
    </body>
    </html>
</#macro>