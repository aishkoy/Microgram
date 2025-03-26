<#import "layout.ftlh" as main>

<@main.layout>
    <div class="max-w-4xl mx-auto my-12 px-4">
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-semibold text-gray-800 mb-6 flex items-center gap-2">
                <i data-lucide="search" class="text-amber-500"></i>
                Find Users
            </h2>

            <form class="mb-8" method="POST" action="/search">
                <div class="space-y-2">
                    <label for="search" class="block text-sm font-medium text-gray-700 flex items-center gap-2">
                        <i data-lucide="user-search" class="text-gray-500"></i>
                        Search by username or email:
                    </label>
                    <div class="flex">
                        <input name="search"
                               id="search"
                               class="flex-grow px-3 py-2 bg-white border border-gray-300 rounded-l-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-amber-500 focus:border-amber-500 sm:text-sm"
                               placeholder="Type username or email..."
                               type="text">
                        <button type="submit"
                                class="bg-amber-500 text-white px-4 py-2 border rounded-r-md hover:bg-amber-600 transition-colors">
                            <i data-lucide="search"></i>
                        </button>
                    </div>
                </div>
            </form>

            <#if results?? && results?size != 0>
                <h2 class="text-xl font-semibold text-gray-800 mb-4 flex items-center gap-2">
                    <i data-lucide="users" class="text-amber-500"></i>
                    Search results:
                </h2>
                <div class="divide-y divide-gray-200">
                    <#list results as user>
                        <a href="/@${user.username}" class="block py-4 px-4 flex items-center gap-4 hover:bg-amber-50 transition-colors rounded-lg">
                            <#if user.avatar?? && user.avatar != "">
                                <img alt="${user.username}"
                                     class="rounded-full h-12 w-12 object-cover border border-gray-300 shadow-sm"
                                     src="/api/post/image/${user.avatar}">
                            <#else>
                                <div class="flex items-center justify-center rounded-full bg-gray-200 h-12 w-12 border border-gray-300">
                                    <i data-lucide="user" class="text-gray-500"></i>
                                </div>
                            </#if>
                            <div>
                                <div class="font-semibold text-gray-800">${user.username}</div>
                                <div class="text-gray-600">${user.name}</div>
                            </div>
                        </a>
                    </#list>
                </div>
            </#if>
        </div>
    </div>
</@main.layout>
