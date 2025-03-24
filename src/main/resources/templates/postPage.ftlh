<#import "layout.ftlh" as l>

<@l.layout>
<body class="bg-gray-50 min-h-screen">
<div class="container mx-auto px-4 py-5">
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <!-- Post Content Section -->
        <div class="border border-gray-200 rounded-lg bg-white shadow-sm h-[800px] overflow-y-auto">
            <div class="p-4 flex flex-col h-full">
                <div class="flex-grow bg-white border border-gray-200 rounded-lg shadow-sm">
                    <div class="px-4 py-3 border-b border-gray-200 font-medium">
                        ${post.owner.username}
                    </div>
                    <div class="p-4">
                        <img src="/api/post/${post.image}" class="max-w-full h-auto rounded" alt="${post.content}">
                    </div>
                    <div class="px-4 py-3 border-t border-gray-200">
                        <p class="font-medium">${post.content}</p>
                        <div id="actions" class="flex items-center mt-2">
                            <button onclick="likePost(event)" class="text-2xl px-2 cursor-pointer flex items-center">
                                <i data-lucide="heart" class="w-6 h-6"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Comments Section -->
        <div class="border border-gray-200 rounded-lg bg-white shadow-sm h-[800px] flex flex-col">
            <!-- Comments List -->
            <div class="p-4 flex-grow overflow-y-auto">
                <div id="commentBox" class="space-y-4">
                    <#if comments??>
                        <#list comments as comment>
                            <div class="mb-4">
                                <div id="${comment.id}" class="border border-gray-200 rounded-lg shadow-sm">
                                    <div class="px-4 py-2 border-b border-gray-200 font-medium bg-gray-50 rounded-t-lg">
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
                                        <#if authEmail == comment.commenter.email>
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
                    </#if>
                </div>
            </div>

            <!-- Comment Input -->
            <div class="p-4 border-t border-gray-200 bg-gray-50 rounded-b-lg">
                <div class="flex gap-2">
                    <input type="text" id="commentInput" placeholder="Type a comment..."
                           class="flex-grow p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <button class="bg-amber-50 text-gray-700 px-4 py-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors flex items-center gap-1 btn-send"
                            type="button">
                        <i data-lucide="send" class="w-4 h-4"></i>
                        Send
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="/js/postPage.js"></script>
<script>
    // Initialize Lucide icons
    lucide.createIcons();

    let lastCommentNum = ${comments?size};

    function likePost(event) {
        const heartIcon = event.currentTarget.querySelector('[data-lucide="heart"]');

        if (heartIcon.classList.contains('text-red-500')) {
            heartIcon.classList.remove('text-red-500', 'fill-red-500');
            heartIcon.classList.add('text-gray-500');
        } else {
            heartIcon.classList.remove('text-gray-500');
            heartIcon.classList.add('text-red-500', 'fill-red-500');
        }
    }
</script>
</body>
</@l.layout>