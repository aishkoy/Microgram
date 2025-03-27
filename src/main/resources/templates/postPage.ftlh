<#import "layout.ftlh" as l>

<@l.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUsername = SPRING_SECURITY_CONTEXT.authentication.name>
        <#assign currentUserId = authorizedUserId>
    </#if>

    <div class="container mx-auto px-4 py-5">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <!-- Post Content Section -->
            <div class="border border-gray-200 rounded-lg bg-white shadow-sm h-[800px] overflow-y-auto">
                <div class="p-4 flex flex-col h-full">
                    <div class="flex-grow bg-white border border-gray-200 rounded-lg shadow-sm">
                        <!-- Post Header with Author Info -->
                        <div class="px-4 py-3 border-b border-gray-200 flex items-center">
                            <#if post.owner.avatar?? && post.owner.avatar?has_content>
                                <img src="/api/posts/image/${post.owner.avatar}"
                                     alt="${post.owner.username}'s avatar"
                                     class="w-8 h-8 rounded-full mr-3 object-cover"/>
                            <#else>
                                <div class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center mr-3">
                                    <i data-lucide="user" class="w-5 h-5 text-gray-300"></i>
                                </div>
                            </#if>
                            <span class="font-medium">@${post.owner.username}</span>
                            <span class="text-gray-400 ml-auto text-sm flex items-center">
                                <i data-lucide="clock" class="w-3.5 h-3.5 mr-1"></i>
                                ${post.postedTime.dayOfMonth}.${post.postedTime.monthValue}.${post.postedTime.year} ${post.postedTime.hour}:${post.postedTime.minute?string("00")}
                            </span>
                        </div>

                        <!-- Post Content Area -->
                        <div class="flex flex-col">
                            <!-- Image if available -->
                            <#if post.image?? && post.image?has_content>
                                <div class="p-4 pb-2">
                                    <img src="/api/posts/image/${post.image}"
                                         class="max-w-full h-auto rounded-lg shadow-sm"
                                         alt="${post.content!''}"/>
                                </div>
                            <#else>
                                <!-- Show placeholder if no image -->
                                <div class="p-4 pb-2">
                                    <div class="bg-gray-50 rounded-lg flex items-center justify-center h-60 border border-gray-100">
                                        <i data-lucide="image-off" class="w-12 h-12 text-gray-300"></i>
                                    </div>
                                </div>
                            </#if>

                            <div class="px-4 py-3">
                                <#if post.content?? && post.content?has_content>
                                    <p class="font-medium text-gray-800">${post.content}</p>
                                <#else>
                                    <p class="text-gray-400 italic text-center">Нет описания</p>
                                </#if>
                            </div>
                        </div>

                        <!-- Post Actions -->
                        <div class="px-4 py-3 border-t border-gray-200">
                            <div id="actions" class="flex items-center">
                                <!-- Like Button -->
                                <button onclick="likePost(event, ${currentUserId}, ${post.id})"
                                        class="flex items-center gap-1 text-gray-600 hover:text-gray-900">
                                    <i data-lucide="heart"
                                       data-post-id="${post.id}"
                                       class="w-5 h-5 <#if post.isLiked>text-red-500 fill-red-500</#if>"></i>
                                </button>

                                <!-- Likes Count -->
                                <#if post.likesNum?? && post.likesNum gt 0>
                                    <span class="ml-1 text-sm text-gray-600">
                                        <#if post.likesNum == 1>
                                            1 like
                                        <#else>
                                            ${post.likesNum} likes
                                        </#if>
                                    </span>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Comments Section -->
            <div class="border border-gray-200 rounded-lg bg-white shadow-sm h-[800px] flex flex-col">
                <!-- Comments Header -->
                <div class="px-4 py-3 border-b border-gray-200 font-medium bg-gray-50">
                    <div class="flex items-center">
                        <i data-lucide="message-circle" class="w-5 h-5 mr-2 text-gray-500"></i>
                        Комментарии
                    </div>
                </div>

                <!-- Comments List -->
                <div class="p-4 flex-grow overflow-y-auto">
                    <div id="commentBox" class="space-y-4">
                        <#if comments?? && comments?size gt 0>
                            <#list comments as comment>
                                <div class="mb-4">
                                    <div id="${comment.id}" class="border border-gray-200 rounded-lg shadow-sm">
                                        <div class="px-4 py-2 border-b border-gray-200 font-medium bg-gray-50 rounded-t-lg flex items-center">
                                            <#if comment.commenter.avatar?? && comment.commenter.avatar?has_content>
                                                <img src="/api/posts/image/${comment.commenter.avatar}"
                                                     alt="Avatar"
                                                     class="w-6 h-6 rounded-full mr-2 object-cover"/>
                                            <#else>
                                                <div class="w-6 h-6 rounded-full bg-gray-100 flex items-center justify-center mr-2">
                                                    <i data-lucide="user" class="w-4 h-4 text-gray-300"></i>
                                                </div>
                                            </#if>
                                            <#if comment.commenter.username??>
                                                ${comment.commenter.username}
                                            <#else>
                                                ${comment.commenter.email}
                                            </#if>
                                        </div>
                                        <div class="px-4 py-3">
                                            ${comment.content}
                                        </div>
                                        <div class="px-4 py-2 border-t border-gray-200 text-sm text-gray-500 flex justify-between items-center rounded-b-lg">
                                            <span>
                                                ${comment.commentedTime.getDayOfMonth()}-${comment.commentedTime.getMonth()}
                                                ${comment.commentedTime.getHour()}:${comment.commentedTime.getMinute()}
                                            </span>
                                            <#if authorizedUserId == comment.commenter.id>
                                                <button class="bg-red-50 hover:bg-red-100 text-red-700 px-3 py-1 rounded-2xl text-sm transition-colors border border-red-200 flex items-center gap-1"
                                                        onclick="deleteComment(${comment.id})">
                                                    <i data-lucide="trash-2" class="w-4 h-4"></i>
                                                    Delete
                                                </button>
                                            </#if>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        <#else>
                            <div class="flex flex-col items-center justify-center py-10 text-gray-400">
                                <i data-lucide="message-square" class="w-12 h-12 mb-3"></i>
                                <p>Нет комментариев. Будьте первым!</p>
                            </div>
                        </#if>
                    </div>
                </div>

                <!-- Comment Input -->
                <div class="p-4 border-t border-gray-200 bg-gray-50 rounded-b-lg">
                    <div class="flex gap-2">
                        <input type="text" id="commentInput" placeholder="Напишите комментарий..."
                               class="flex-grow p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-amber-500">
                        <button class="bg-amber-50 text-gray-700 px-4 py-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors flex items-center gap-1 btn-send"
                                type="button">
                            <i data-lucide="send" class="w-4 h-4"></i>
                            Отправить
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="/js/postPage.js"></script>
    <script src="/js/likes.js"></script>

</@l.layout>