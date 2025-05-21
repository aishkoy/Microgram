<#import "../layout.ftlh" as layout>
<@layout.page title="" styles="" activeNav="profile">

    <div class="d-flex align-items-center mb-4">
        <img src="${user.avatar?default('/static/avatar/default_avatar.jpg')}" alt="Avatar" class="rounded-circle me-4" width="100" height="100">
        <div>
            <h4>${user.username}</h4>
            <div class="d-flex gap-4 profile-stats">
                <div><strong>0</strong><br>публикаций</div>
                <div><strong>${user.followersCount}</strong><br>подписчиков</div>
                <div><strong>${user.followingCount}</strong><br>подписок</div>
            </div>
            <p class="mt-2 mb-0">${user.name} ${user.surname}</p>
            <p>${user.bio?default("")}</p>
        </div>
    </div>

    <hr>

    <div class="text-center">
        <p>Поделитесь фото</p>
        <p class="text-muted">Фото, которыми вы делитесь, будут показываться в вашем профиле.</p>
        <a href="#" class="btn btn-primary">Поделитесь своим первым фото</a>
    </div>
</@layout.page>
