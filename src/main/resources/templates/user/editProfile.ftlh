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
                        <form action="/profile/edit" method="post" enctype="multipart/form-data">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>
                            <div class="mb-3 text-center">
                                <img src="/api/users/${editUserDto.id}/avatar" alt="Аватар" class="rounded-circle me-4"  width="100" height="100"
                                     onerror="this.src='/static/avatar/default_avatar.jpg'">
                                <br>
                                <button type="button" class="btn btn-sm btn-outline-primary mt-2" data-bs-toggle="modal" data-bs-target="#avatarModal">Изменить аватар</button>
                            </div>

                            <div class="modal fade" id="avatarModal" tabindex="-1" aria-labelledby="avatarModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="avatarModalLabel">Обновить аватар</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                        </div>
                                        <div class="modal-body">
                                            <input type="file" class="form-control mb-2" name="avatarFile" accept="image/*">
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                                            <button type="submit" class="btn btn-primary">Готово</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="name" class="form-label">Имя</label>
                                <@spring.bind "editUserDto.name"/>
                                <input type="text" class="form-control ${spring.status.error?string('is-invalid', '')}" id="name" name="name" value="${spring.status.value!}">
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="mb-3">
                                <label for="surname" class="form-label">Фамилия</label>
                                <@spring.bind "editUserDto.surname"/>
                                <input type="text" class="form-control ${spring.status.error?string('is-invalid', '')}" id="surname" name="surname" value="${spring.status.value!}">
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="mb-3">
                                <label for="bio" class="form-label">Био</label>
                                <@spring.bind "editUserDto.bio"/>
                                <textarea class="form-control ${spring.status.error?string('is-invalid', '')}" id="bio" name="bio" rows="3">${spring.status.value!}</textarea>
                                <@spring.showErrors "<div class='invalid-feedback'>", "</div>"/>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Сохранить изменения</button>
                                <a href="/profile" class="btn btn-outline-secondary">Отмена</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.page>