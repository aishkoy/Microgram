<#import "layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUsername = SPRING_SECURITY_CONTEXT.authentication.name>
        <#assign currentUserId = authorizedUserId>
    </#if>

    <div class="container max-w-2xl mx-auto px-4 py-5">
        <h1 class="text-2xl font-bold mb-6 text-center">Понравившиеся посты</h1>

        <#if posts??>
            <#if posts?size != 0>
                <div class="space-y-0">
                    <#list posts as post>
                        <#assign isFirstPost = post?index == 0>
                        <#assign isLastPost = post?index == posts?size - 1>
                        <#assign isOnlyPost = posts?size == 1>

                        <div class="p-4 border border-gray-400
                            <#if isOnlyPost>
                                rounded-lg
                            <#else>
                                <#if isFirstPost>rounded-t-lg</#if>
                                <#if isLastPost>rounded-b-lg border-b<#else>border-b-0</#if>
                            </#if>
                        ">
                            <a href="/@${post.owner.username}" class="inline-flex items-center justify-center text-gray-900">
                                <#if post.owner.avatar?? && post.owner.avatar?has_content>
                                    <img src="/api/posts/image/${post.owner.avatar}"
                                         alt="${post.owner.username}'s avatar"
                                         class="w-10 h-10 rounded-full mr-3 object-cover cursor-pointer" />
                                <#else>
                                    <div>
                                        <i data-lucide="user"  class="w-10 h-10 rounded-full mr-3 object-cover cursor-pointer" style="color: #d1d5db;"></i>
                                    </div>
                                </#if>
                                <span class="text-gray-600">@${post.owner.username}</span>
                                <span class="text-gray-400 ml-auto text-sm flex items-center">
                                <i data-lucide="clock" class="w-3.5 h-3.5 mr-1"></i>
                                ${post.postedTime.dayOfMonth}.${post.postedTime.monthValue}.${post.postedTime.year} ${post.postedTime.hour}:${post.postedTime.minute?string("00")}
                            </span>
                            </a>

                            <#if post.image?? && post.image?has_content>
                                <img src="/api/posts/image/${post.image}"
                                     alt="${post.content}"
                                     class="w-full h-60 object-cover rounded-lg my-3 cursor-pointer"
                                     onclick="window.location.href='/posts/${post.id}'" />
                            </#if>

                            <p class="mt-2">${post.content}</p>

                            <div class="flex items-center mt-3">
                                <#if known && currentUserId??>
                                    <button onclick="likePost(event, ${currentUserId}, ${post.id})" class="flex items-center gap-1 text-gray-600 hover:text-gray-900">
                                        <i data-lucide="heart"
                                           data-post-id="${post.id}"
                                           class="w-5 h-5 <#if post.isLiked>text-red-500 fill-red-500</#if>"></i>
                                    </button>
                                <#else>
                                    <a href="/login" class="flex items-center gap-1 text-gray-600 hover:text-gray-900">
                                        <i data-lucide="heart" class="w-5 h-5"></i>
                                    </a>
                                </#if>

                                <button onclick="window.location.href='/posts/${post.id}'" class="flex items-center gap-1 ml-4 text-gray-600 hover:text-gray-900">
                                    <i data-lucide="message-circle" class="w-5 h-5"></i>
                                </button>
                            </div>

                            <#if post.likesNum??>
                                <#if post.likesNum == 1>
                                    <div class="text-sm font-bold mt-1">1 лайк</div>
                                <#elseif post.likesNum gt 1>
                                    <div class="text-sm font-bold mt-1">${post.likesNum} лайков</div>
                                </#if>
                            </#if>

                            <a href="/posts/${post.id}" class="text-sm text-gray-500 hover:text-gray-700 mt-2 inline-block">
                                Смотреть комментарии
                            </a>
                        </div>
                    </#list>
                </div>
            <#else>
                <div class="text-center py-10">
                    <h5 class="text-gray-600">Постов пока нет...</h5>
                </div>
            </#if>
        <#else>
            <div class="text-center py-10">
                <h5 class="text-gray-600">Нет доступных постов</h5>
            </div>
        </#if>
    </div>

    <script src="/js/likes.js"></script>
</@main.layout>