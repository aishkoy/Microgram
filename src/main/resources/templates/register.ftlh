<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registration</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
</head>
<body class="bg-gray-50 min-h-screen">

<div class="container mx-auto px-4 py-8 mt-5">
    <div class="max-w-2xl mx-auto">
        <h1 class="text-3xl font-bold mb-6">Sign Up</h1>

        <form action="/register" method="post" class="bg-white p-6 rounded-lg shadow-sm border border-gray-200">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                <div>
                    <label for="name" class="block text-sm font-medium text-gray-700 mb-1">Name</label>
                    <input type="text" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                           id="name" name="name">
                </div>
                <div>
                    <label for="surname" class="block text-sm font-medium text-gray-700 mb-1">Surname</label>
                    <input type="text" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                           id="surname" name="surname">
                </div>
            </div>

            <div class="mb-4">
                <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Username</label>
                <input type="text" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                       id="username" name="username">
                <#if usernameError??>
                    <p class="text-red-500 text-sm mt-1">${usernameError}</p>
                </#if>
            </div>

            <div class="mb-4">
                <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input type="email" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                       id="email" name="email">
            </div>

            <div class="mb-4">
                <label for="password" class="block text-sm font-medium text-gray-700 mb-1">Password</label>
                <input type="password" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                       id="password" name="password">
                <#if passwordError??>
                    <p class="text-red-500 text-sm mt-1">${passwordError}</p>
                </#if>
            </div>

            <div class="mb-4">
                <label for="gender" class="block text-sm font-medium text-gray-700 mb-1">Gender</label>
                <select class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        id="gender" name="gender">
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                </select>
            </div>

            <#if error??>
                <p class="text-red-500 text-sm mb-4">${error}</p>
            </#if>

            <#if success??>
                <p class="text-green-500 text-sm mb-4">${success}</p>
            </#if>

            <button type="submit" class="bg-amber-50 text-gray-700 p-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors">
                Sign Up
            </button>
        </form>

        <p class="mt-4 text-center">Already have an account? <a href="/login" class="text-blue-400 hover:text-blue-500 transition-colors">Log in</a></p>
    </div>
</div>

</body>
</html>