<#macro layout>
    <!DOCTYPE html>
    <html lang="en" class="light">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Suslike</title>
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
        <script src="https://unpkg.com/lucide@latest"></script>
        <script>
            // Check for saved theme preference or respect OS preference
            if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
                document.documentElement.classList.add('dark');
                document.documentElement.classList.remove('light');
            } else {
                document.documentElement.classList.add('light');
                document.documentElement.classList.remove('dark');
            }
        </script>
    </head>
    <body class="bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-gray-100 min-h-screen transition-colors duration-300">
    <#-- Check if user is logged in -->
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authUser = SPRING_SECURITY_CONTEXT.authentication.principal.user!{}>
        <#if authUser?has_content && authUser.username?has_content>
            <#assign username = authUser.username>
        <#else>
            <#assign username = SPRING_SECURITY_CONTEXT.authentication.name>
        </#if>
        <#assign myProfileUrl = "/@" + username>
    <#else>
        <#assign myProfileUrl = "/login">
    </#if>


    <!-- Navigation Sidebar - Desktop -->
    <nav class="fixed left-0 top-0 w-20 h-screen bg-white dark:bg-gray-800 shadow-sm dark:shadow-gray-700 border-r border-gray-200 dark:border-gray-700 flex flex-col items-center py-6 z-50 transition-colors duration-300 sm:block hidden">
        <div class="flex flex-col items-center h-full w-full">
            <!-- Top Logo Area -->
            <div class="mb-8">
                <a href="/" class="text-xl font-bold">
                    S
                </a>
            </div>

            <!-- Main Navigation -->
            <ul class="flex flex-col gap-8 flex-1">
                <li>
                    <a href="/" class="flex justify-center p-2 hover:scale-110 transition-transform duration-200">
                        <i data-lucide="home" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 transition-colors duration-200"></i>
                    </a>
                </li>
                <li>
                    <a href="/search" class="flex justify-center p-2 hover:scale-110 transition-transform duration-200">
                        <i data-lucide="search" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 transition-colors duration-200"></i>
                    </a>
                </li>
                <li>
                    <a href="/posts" class="flex justify-center p-2 hover:scale-110 transition-transform duration-200">
                        <i data-lucide="plus-circle" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 transition-colors duration-200"></i>
                    </a>
                </li>
                <li>
                    <a href="posts/favorites" class="flex justify-center p-2 hover:scale-110 transition-transform duration-200">
                        <i data-lucide="heart" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 transition-colors duration-200"></i>
                    </a>
                </li>
                <li>
                    <a href="${myProfileUrl}" class="flex justify-center p-2 hover:scale-110 transition-transform duration-200">
                        <i data-lucide="user" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 transition-colors duration-200"></i>
                    </a>
                </li>
            </ul>

            <!-- Theme Switcher -->
            <div class="mt-auto">
                <button id="theme-toggle" class="p-2 rounded-full hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors duration-200">
                    <i data-lucide="sun" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 hidden dark:block" id="dark-icon"></i>
                    <i data-lucide="moon" class="w-7 h-7 stroke-[1.5px] text-gray-500 dark:text-gray-400 block dark:hidden" id="light-icon"></i>
                </button>
            </div>
        </div>
    </nav>

    <!-- Navigation Bottom Bar - Mobile -->
    <nav class="fixed bottom-0 left-0 w-full bg-white dark:bg-gray-800 shadow-sm dark:shadow-gray-700 border-t border-gray-200 dark:border-gray-700 py-3 px-4 z-50 transition-colors duration-300 sm:hidden block">
        <ul class="flex justify-around items-center">
            <li>
                <a href="/" class="flex justify-center p-2">
                    <i data-lucide="home" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400"></i>
                </a>
            </li>
            <li>
                <a href="/search" class="flex justify-center p-2">
                    <i data-lucide="search" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400"></i>
                </a>
            </li>
            <li>
                <a href="/posts" class="flex justify-center p-2">
                    <i data-lucide="plus-circle" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400"></i>
                </a>
            </li>
            <li>
                <a href="/posts/favorites" class="flex justify-center p-2">
                    <i data-lucide="heart" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400"></i>
                </a>
            </li>
            <li>
                <a href="${myProfileUrl}" class="flex justify-center p-2">
                    <i data-lucide="user" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400"></i>
                </a>
            </li>
            <li>
                <button id="mobile-theme-toggle" class="p-2 flex justify-center">
                    <i data-lucide="sun" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400 hidden dark:block"></i>
                    <i data-lucide="moon" class="w-6 h-6 stroke-[1.5px] text-gray-500 dark:text-gray-400 block dark:hidden"></i>
                </button>
            </li>
        </ul>
    </nav>

    <!-- Main Content -->
    <div class="sm:ml-20 sm:w-[calc(100%-5rem)] w-full pb-16 sm:pb-0 transition-all duration-300">
        <main class="container mx-auto px-4 py-5">
            <#nested>
        </main>
    </div>

    <script>
        lucide.createIcons();

        function toggleTheme() {
            if (document.documentElement.classList.contains('dark')) {
                document.documentElement.classList.remove('dark');
                document.documentElement.classList.add('light');
                localStorage.theme = 'light';
            } else {
                document.documentElement.classList.remove('light');
                document.documentElement.classList.add('dark');
                localStorage.theme = 'dark';
            }

            // Refresh icons to update theme
            lucide.createIcons();
        }

        // Add event listeners to both desktop and mobile toggle buttons
        document.getElementById('theme-toggle').addEventListener('click', toggleTheme);
        document.getElementById('mobile-theme-toggle').addEventListener('click', toggleTheme);

        // Mark active links
        document.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const navLinks = document.querySelectorAll('nav a');

            navLinks.forEach(link => {
                const href = link.getAttribute('href');
                if (currentPath === href ||
                    (href !== '/' && currentPath.startsWith(href))) {
                    // Add active classes with Tailwind
                    const icon = link.querySelector('i');
                    if (icon) {
                        icon.classList.add('text-gray-900', 'dark:text-white');
                        icon.classList.remove('text-gray-500', 'dark:text-gray-400');
                    }
                }
            });
        });
    </script>
    </body>
    </html>
</#macro>