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
        <img src="/api/users/${user.id}/avatar" alt="Аватар" class="rounded-circle me-4"  width="100" height="100"
             onerror="this.src='/static/avatar/default_avatar.jpg'">
        <div>
            <h4>${user.username}</h4>
            <div class="d-flex gap-4 profile-stats">
                <div><strong>0</strong><br>публикаций</div>
                <div><strong>${user.followersCount}</strong><br>подписчиков</div>
                <div><strong>${user.followingCount}</strong><br>подписок</div>
            </div>
            <p class="mt-2 mb-0">
                ${user.name}<#if user.surname?? && user.surname?has_content> ${user.surname}</#if>
            </p>
            <p>${user.bio?default("")}</p>
            <a href="/profile/edit" class="btn btn-outline-secondary btn-sm">Редактировать профиль</a>
        </div>
    </div>

    <hr>

    <div class="text-center">
        <p>Поделитесь фото</p>
        <p class="text-muted">Фото, которыми вы делитесь, будут показываться в вашем профиле.</p>
        <a href="#" class="btn btn-primary">Поделитесь своим первым фото</a>
    </div>
</@layout.page>
