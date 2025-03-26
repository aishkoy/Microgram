<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Suslike - Вход</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
</head>
<body class="bg-gray-50 min-h-screen flex flex-col">

<div class="flex flex-col items-center justify-center flex-grow px-4 py-12">
    <div class="w-full max-w-md">
        <div class="bg-white rounded-lg shadow-md p-8">
            <h2 class="text-2xl font-bold text-center mb-6">Вход в Suslike</h2>

            <form action="/login" method="post" class="space-y-6">
                <div>
                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Email или имя пользователя</label>
                    <input type="text" id="username" name="username"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-amber-500 focus:border-amber-500"
                           placeholder="Введите email или имя пользователя" required />
                </div>

                <div>
                    <label for="password" class="block text-sm font-medium text-gray-700 mb-1">Пароль</label>
                    <input type="password" id="password" name="password"
                           class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-amber-500 focus:border-amber-500"
                           placeholder="Введите пароль" required />
                </div>

                <div class="flex items-center">
                    <input id="remember-me" name="remember-me" type="checkbox"
                           class="h-4 w-4 text-amber-600 focus:ring-amber-500 border-gray-300 rounded" />
                    <label for="remember-me" class="ml-2 block text-sm text-gray-700">
                        Запомнить меня
                    </label>
                </div>

                <div>
                    <button type="submit"
                            class="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-amber-600 hover:bg-amber-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-amber-500">
                        Войти
                    </button>
                </div>

                <div class="text-center">
                    <p class="text-sm text-gray-600">
                        Нет аккаунта?
                        <a href="/register" class="font-medium text-amber-600 hover:text-amber-500">
                            Зарегистрироваться
                        </a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>

<footer class="bg-amber-600 py-4 px-6 w-full mt-auto">
    <div class="text-center text-white">
        Copyright © 2025. Все права защищены. Suslike.
    </div>
</footer>
</body>
</html>