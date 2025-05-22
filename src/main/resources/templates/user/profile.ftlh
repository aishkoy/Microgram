<#import "../layout.ftlh" as layout>
<@layout.page title="" styles="" activeNav="profile">
    <script src="/static/Js/notifications.js"></script>

    <#if successMessage??>
        <div class="alert alert-success mb-4 shadow-sm">
            ${successMessage}
        </div>
    </#if>

    <#if errorMessage??>
        <div class="alert alert-danger mb-4 shadow-sm">
            ${errorMessage}
        </div>
    </#if>

    <div class="d-flex align-items-center mb-4">
        <img src="/api/users/${user.id}/avatar" alt="Аватар" class="rounded-circle me-4" width="100" height="100"
             onerror="this.src='/static/avatar/default_avatar.jpg'">
        <div>
            <h4>${user.username}</h4>
            <div class="d-flex gap-4 profile-stats">
                <div><strong>${posts?size}</strong><br>публикаций</div>
                <div><strong>${user.followers}</strong><br>подписчиков</div>
                <div><strong>${user.followings}</strong><br>подписок</div>
            </div>
            <p class="mt-2 mb-0">
                ${user.name}<#if user.surname?? && user.surname?has_content> ${user.surname}</#if>
            </p>
            <p>${user.bio!""}</p>
            <a href="/profile/edit" class="btn btn-outline-secondary btn-sm">Редактировать профиль</a>
        </div>
    </div>

    <hr>

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
                <p>Поделитесь фото</p>
                <p class="text-muted">Фото, которыми вы делитесь, будут показываться в вашем профиле.</p>
                <a href="/posts/create" class="btn btn-primary">Поделитесь своим первым фото</a>
            </div>
        </#if>
    </div>

    <#if isCurrentUser && posts?? && posts?size gt 0>
        <div class="text-center mt-4">
            <a href="/posts/create" class="btn btn-primary">Добавить новый пост</a>
        </div>
    </#if>

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