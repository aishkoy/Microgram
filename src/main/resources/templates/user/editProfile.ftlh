<#import "/spring.ftl" as spring>
<#import "../layout.ftlh" as layout>

<@layout.page title="Редактирование профиля" styles="/static/css/profile-edit.css" activeNav="profile">
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-8 offset-md-2">
                <div class="card">
                    <div class="card-header">
                        <h4>Редактирование профиля</h4>
                    </div>
                    <div class="card-body">
                        <div class="mb-3 text-center">
                            <img src="/api/users/${editUserDto.id}/avatar" alt="Аватар" class="rounded-circle me-4"
                                 width="100" height="100"
                                 onerror="this.src='/static/avatar/default_avatar.jpg'">
                            <br>
                            <form method="POST" action="/users/${editUserDto.id}/avatar" enctype="multipart/form-data"
                                  class="mt-4">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </#if>
                                <label for="avatar-upload"
                                       class="cursor-pointer px-4 py-2 text-sm font-medium text-white bg-blue-600 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                                    Изменить фото
                                </label>
                                <input id="avatar-upload" name="file" type="file" accept="image/jpeg, image/png"
                                       class="hidden"
                                       onchange="this.form.submit()">
                            </form>
                        </div>
                        <form action="/users/${editUserDto.id}/edit" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${editUserDto.id}">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>

                            <div class="mb-3">
                                <label for="name" class="form-label">Имя</label>
                                <@spring.bind "editUserDto.name"/>
                                <input type="text" class="form-control ${spring.status.error?string('is-invalid', '')}"
                                       id="name" name="name" value="${spring.status.value!}">
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="mb-3">
                                <label for="surname" class="form-label">Фамилия</label>
                                <@spring.bind "editUserDto.surname"/>
                                <input type="text" class="form-control ${spring.status.error?string('is-invalid', '')}"
                                       id="surname" name="surname" value="${spring.status.value!}">
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="mb-3">
                                <label for="bio" class="form-label">Био</label>
                                <@spring.bind "editUserDto.bio"/>
                                <textarea class="form-control ${spring.status.error?string('is-invalid', '')}" id="bio"
                                          name="bio" rows="3">${spring.status.value!}</textarea>
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Сохранить изменения</button>
                                <a href="/users/${editUserDto.id}" class="btn btn-outline-secondary">Отмена</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.page>