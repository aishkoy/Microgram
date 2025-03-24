<#import "layout.ftlh" as main>

<@main.layout>
<#assign known = SPRING_SECURITY_CONTEXT??>
<#if known>
    <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.name>
</#if>
<body class="bg-gray-50 min-h-screen">

<div class="container max-w-2xl mx-auto px-4 py-5">
    <div class="flex justify-between items-center mb-4 gap-3 border border-gray-400 rounded-lg p-3 cursor-pointer"
         onclick="window.location.href='/post'">
        <input
                placeholder="What's new?"
                class="w-full p-2 rounded focus:outline-none cursor-pointer"
                readonly
        />
        <button type="button"
                class="bg-amber-50 text-gray-700 px-4 py-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors">
            Post
        </button>
    </div>

    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.name>
    </#if>

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
                        <a href="/user/profile/${post.owner.username}" class="inline-flex items-center justify-center text-gray-900">
                            <#if post.owner.avatar??>
                                <img src="/api/post/${post.owner.avatar}"
                                     alt="${post.owner.username}'s avatar"
                                     class="w-10 h-10 rounded-full mr-3 object-cover cursor-pointer" />
                            <#else>
                                <img src="https://images.squarespace-cdn.com/content/v1/54b7b93ce4b0a3e130d5d232/1519987020970-8IQ7F6Z61LLBCX85A65S/icon.png?format=500w"
                                     alt="${post.owner.username}'s avatar"
                                     class="w-10 h-10 rounded-full mr-3 object-cover cursor-pointer" />
                            </#if>
                            <span class="text-gray-600">@${post.owner.username}</span>
                            <span class="text-gray-400 ml-auto text-sm">${post.postedTime}</span>
                        </a>

                        <#if post.image??>
                            <img src="/api/post/${post.image}"
                                 alt="${post.content}"
                                 class="w-full h-60 object-cover rounded-lg my-3 cursor-pointer"
                                 onclick="window.location.href='/${post.id}'" />
                        </#if>

                        <p class="mt-2">${post.content}</p>

                        <div class="flex items-center mt-3">
                            <button onclick="likePost(event)" class="flex items-center gap-1 text-gray-600 hover:text-gray-900">
                                <i data-lucide="heart"
                                   id="${authorizedUserEmail}/${post.id}"
                                   class="w-5 h-5 <#if post.isLiked?? && post.isLiked>text-red-500 fill-red-500</#if>"></i>
                            </button>

                            <button onclick="window.location.href='/${post.id}'" class="flex items-center gap-1 ml-4 text-gray-600 hover:text-gray-900">
                                <i data-lucide="message-circle" class="w-5 h-5"></i>
                            </button>
                        </div>

                        <#if post.likesNum??>
                            <#if post.likesNum == 1>
                                <div class="text-sm font-bold mt-1">1 like</div>
                            <#elseif post.likesNum gt 1>
                                <div class="text-sm font-bold mt-1">${post.likesNum} likes</div>
                            </#if>
                        </#if>

                        <a href="/${post.id}" class="text-sm text-gray-500 hover:text-gray-700 mt-2 inline-block">
                            View comments
                        </a>
                    </div>
                </#list>
            </div>
        <#else>
            <div class="text-center py-10">
                <h5 class="text-gray-600">Nothing new...</h5>
            </div>
        </#if>
    <#else>
        <div class="text-center py-10">
            <h5 class="text-gray-600"><a href="/login" class="text-blue-500 hover:text-blue-700">Log in</a> to see what people in your following are posting.</h5>
        </div>
    </#if>
</div>

<script>
    lucide.createIcons();

    async function likePost(event) {
        event.preventDefault();

        const heartIcon = event.currentTarget.querySelector('[data-lucide="heart"]');
        const id = heartIcon.id;
        const [authorizedUserEmail, postId] = id.split('/');

        try {
            const response = await fetch('/api/like/like', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    likerEmail: authorizedUserEmail,
                    postId: postId
                })
            });

            if (response.ok) {
                if (heartIcon.classList.contains('text-red-500')) {
                    heartIcon.classList.remove('text-red-500', 'fill-red-500');
                } else {
                    heartIcon.classList.add('text-red-500', 'fill-red-500');
                }
            } else {
                throw new Error('Failed to like post');
            }
        } catch (error) {
            console.error('Error liking post:', error);
        }
    }

    window.addEventListener('load', async () => {
        try {
            const response = await fetch('/api/like/isLiked');
            if (response.ok) {
                console.log('Likes loaded successfully');
            }
        } catch (error) {
            console.error('Error loading likes:', error);
        }
    });
</script>
</@main.layout>