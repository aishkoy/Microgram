<#import "../layout.ftlh" as layout>
<@layout.page title="Все посты" styles="" activeNav="explore">

    <div class="row posts-grid mt-4">
        <#if posts?? && posts?size gt 0>
            <#list posts as post>
                <div class="col-md-4 col-sm-6 mb-4">
                    <a href="/posts/${post.id}" class="post-item">
                        <div class="card post-card">
                            <img src="/api/posts/${post.id}/image" class="card-img-top" alt="Пост"
                                 style="height: 300px; object-fit: cover;">
                            <div class="post-overlay">
                                <div class="post-info">
                                    <span><i class="bi bi-heart-fill"></i> ${post.likes!"0"}</span>
                                    <span><i class="bi bi-chat-fill"></i> ${post.comments!"0"}</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </#list>
        <#else>
            <div class="text-center mt-5">
                <p>Пока нет ни одного поста</p>
                <a href="/posts/create" class="btn btn-primary">Создать пост</a>
            </div>
        </#if>
    </div>

    <style>
        .post-card {
            position: relative;
            overflow: hidden;
        }

        .post-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            opacity: 0;
            transition: opacity 0.3s;
            color: white;
        }

        .post-item:hover .post-overlay {
            opacity: 1;
        }

        .post-info {
            display: flex;
            gap: 20px;
        }

        .post-info span {
            display: flex;
            align-items: center;
            gap: 5px;
            font-weight: bold;
        }
    </style>

</@layout.page>
