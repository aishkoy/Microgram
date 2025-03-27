<#import "layout.ftlh" as l>

<@l.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUsername = SPRING_SECURITY_CONTEXT.authentication.name>
        <#assign isOwner = authorizedUsername?? && authorizedUsername == user.username>
        <#assign currentUserId = authorizedUserId>
    </#if>

    <!-- Main Container -->
    <div class="max-w-4xl mx-auto bg-white rounded-lg shadow-sm overflow-hidden">
        <!-- Profile Header -->
        <div class="w-full px-4 py-6">
            <div class="flex flex-col md:flex-row items-center">
                <!-- Avatar Section -->
                <div class="w-full md:w-1/4 flex justify-center mb-6 md:mb-0">
                    <div class="relative cursor-pointer">
                        <#if isOwner>
                        <a href="/profile/avatar" class="block">
                            <#else>
                            <a href="/@${user.username}/avatar" class="block">
                                </#if>
                                <#if user.avatar?? && user.avatar?has_content>
                                    <img src="/api/posts/image/${user.avatar}" alt="${user.username}'s avatar"
                                         class="w-28 h-28 rounded-full object-cover border-2 border-gray-200">
                                <#else>
                                    <div class="w-28 h-28 rounded-full bg-gray-100 flex items-center justify-center">
                                        <i data-lucide="user" class="w-14 h-14 text-gray-300"></i>
                                    </div>
                                </#if>
                            </a>
                    </div>
                </div>

                <!-- Profile Info Section -->
                <div class="w-full md:w-3/4">
                    <div class="flex flex-col sm:flex-row justify-between items-center sm:items-start mb-4">
                        <div class="text-center sm:text-left mb-4 sm:mb-0">
                            <h1 class="text-2xl font-bold text-gray-900">${user.username}</h1>
                            <#if user.name?? && user.surname??>
                                <p class="text-gray-500">${user.name} ${user.surname}</p>
                            </#if>
                        </div>

                        <div>
                            <#if authorizedUsername??>
                                <#if isOwner>
                                    <a href="/profile"
                                       class="inline-flex items-center px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-800 rounded-md transition">
                                        <i data-lucide="edit-3" class="w-4 h-4 mr-2"></i>
                                        Edit Profile
                                    </a>
                                <#else>
                                    <#assign found = false>
                                    <#list followings as following>
                                        <#if following.actualUser == user.id>
                                            <#assign found = true>
                                            <#break>
                                        </#if>
                                    </#list>

                                    <form id="followForm" class="${found?string('hidden', 'inline-block')}">
                                        <button type="submit"
                                                class="inline-flex items-center px-4 py-2 bg-amber-500 hover:bg-amber-600 text-white rounded-md transition">
                                            <i data-lucide="user-plus" class="w-4 h-4 mr-2"></i> Follow
                                        </button>
                                    </form>
                                    <form id="unfollowForm" class="${found?string('inline-block', 'hidden')}">
                                        <button type="submit"
                                                class="inline-flex items-center px-4 py-2 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-md transition">
                                            <i data-lucide="user-minus" class="w-4 h-4 mr-2"></i> Unfollow
                                        </button>
                                    </form>
                                </#if>
                            </#if>
                        </div>
                    </div>

                    <!-- Stats Section -->
                    <div class="flex justify-center sm:justify-start space-x-8 mb-4">
                        <div class="flex flex-col items-center sm:items-start">
                            <span class="font-bold text-gray-900">${posts?size!0}</span>
                            <span class="text-gray-500 text-sm">Posts</span>
                        </div>

                        <div id="followers-count-container" class="flex flex-col items-center sm:items-start cursor-pointer hover:opacity-80 transition">
                            <span class="font-bold text-gray-900" id="followers-count">0</span>
                            <span class="text-gray-500 text-sm">Followers</span>
                        </div>

                        <div id="following-count-container" class="flex flex-col items-center sm:items-start cursor-pointer hover:opacity-80 transition">
                            <span class="font-bold text-gray-900" id="following-count">0</span>
                            <span class="text-gray-500 text-sm">Following</span>
                        </div>
                    </div>

                    <!-- Bio Section -->
                    <#if user.aboutMe??>
                        <div class="mt-4 text-center sm:text-left">
                            <p class="text-gray-700">${user.aboutMe}</p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>

        <!-- Posts Section -->
        <div class="w-full px-4 py-6">
            <div class="border-b border-gray-200 mb-6">
                <h2 class="text-xl font-bold text-gray-900 pb-3">Posts</h2>
            </div>

            <#if posts?? && posts?size gt 0>
                <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
                    <#list posts as post>
                        <div class="rounded-lg overflow-hidden bg-white shadow-sm hover:shadow-md transition">
                            <a href="/posts/${post.id}" class="block">
                                <div class="relative">
                                    <#if post.image?? && post.image?has_content>
                                        <img src="/api/posts/image/${post.image}" class="w-full aspect-square object-cover"
                                             alt="Post image">
                                        <div class="absolute inset-0 bg-black bg-opacity-20 opacity-0 hover:opacity-100 transition flex items-center justify-center text-white">
                                            <div class="flex space-x-4">
                                                <div class="flex items-center">
                                                    <i data-lucide="heart" class="w-5 h-5 mr-1"></i>
                                                    <span>${post.likesNum}</span>
                                                </div>
                                                <div class="flex items-center">
                                                    <i data-lucide="message-circle" class="w-5 h-5 mr-1"></i>
                                                    <span>${post.commentsNum}</span>
                                                </div>
                                            </div>
                                        </div>
                                    <#else>
                                        <div class="w-full aspect-square bg-gray-100 flex items-center justify-center">
                                            <div class="text-center">
                                                <i data-lucide="file-text" class="w-12 h-12 mx-auto mb-2 text-gray-300"></i>
                                                <p class="text-gray-500 px-4 truncate">${post.content!"Text post"}</p>
                                            </div>
                                        </div>
                                        <div class="absolute inset-0 bg-black bg-opacity-10 opacity-0 hover:opacity-100 transition flex items-center justify-center text-white">
                                            <div class="flex space-x-4">
                                                <div class="flex items-center">
                                                    <i data-lucide="heart" class="w-5 h-5 mr-1"></i>
                                                    <span>${post.likesNum}</span>
                                                </div>
                                                <div class="flex items-center">
                                                    <i data-lucide="message-circle" class="w-5 h-5 mr-1"></i>
                                                    <span>${post.commentsNum}</span>
                                                </div>
                                            </div>
                                        </div>
                                    </#if>
                                </div>
                            </a>
                            <div class="p-3">
                                <p class="text-gray-500 text-sm flex items-center">
                                     <span class="text-gray-400 ml-auto text-sm flex items-center">
                                <i data-lucide="calendar" class="w-3.5 h-3.5 mr-1"></i>
                                ${post.postedTime.dayOfMonth}.${post.postedTime.monthValue}.${post.postedTime.year} ${post.postedTime.hour}:${post.postedTime.minute?string("00")}
                            </span>
                                </p>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <div class="flex flex-col items-center justify-center py-10 text-gray-400">
                    <i data-lucide="image" class="w-12 h-12 mb-4"></i>
                    <p class="text-lg">No posts yet</p>
                </div>
            </#if>
        </div>
    </div>

    <!-- Followers Modal -->
    <div id="followers-modal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
        <div class="bg-white rounded-lg shadow-lg w-full max-w-md mx-4">
            <div class="border-b border-gray-200 px-6 py-4 flex justify-between items-center">
                <h3 class="text-lg font-semibold text-gray-800">Followers</h3>
                <button class="modal-close text-gray-500 hover:text-gray-700 focus:outline-none" data-modal-id="followers-modal">
                    <i data-lucide="x" class="w-5 h-5"></i>
                </button>
            </div>
            <div id="followers-modal-content" class="max-h-80 overflow-y-auto">
                <!-- Content will be loaded by JavaScript -->
                <div class="flex justify-center items-center p-6">
                    <i data-lucide="loader" class="w-8 h-8 animate-spin text-gray-400"></i>
                </div>
            </div>
            <div class="border-t border-gray-200 px-6 py-4">
                <button class="modal-close w-full px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-800 rounded-md transition" data-modal-id="followers-modal">
                    Close
                </button>
            </div>
        </div>
    </div>

    <!-- Following Modal -->
    <div id="following-modal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden">
        <div class="bg-white rounded-lg shadow-lg w-full max-w-md mx-4">
            <div class="border-b border-gray-200 px-6 py-4 flex justify-between items-center">
                <h3 class="text-lg font-semibold text-gray-800">Following</h3>
                <button class="modal-close text-gray-500 hover:text-gray-700 focus:outline-none" data-modal-id="following-modal">
                    <i data-lucide="x" class="w-5 h-5"></i>
                </button>
            </div>
            <div id="following-modal-content" class="max-h-80 overflow-y-auto">
                <div class="flex justify-center items-center p-6">
                    <i data-lucide="loader" class="w-8 h-8 animate-spin text-gray-400"></i>
                </div>
            </div>
            <div class="border-t border-gray-200 px-6 py-4">
                <button class="modal-close w-full px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-800 rounded-md transition" data-modal-id="following-modal">
                    Close
                </button>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/js/profile.js"></script>
    <script src="/js/follows.js"></script>
    <script>
        window.addEventListener("load", () => {
            window.followsJS.initFollows(${currentUserId!"null"}, ${user.id}, "${user.username}");
        });
    </script>
</@l.layout>