<#import "../layout.ftlh" as layout>
<#import "/spring.ftl" as spring>

<@layout.page title="Регистрация" styles="" activeNav="login">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card mt-5">
                    <div class="card-body">
                        <h3 class="card-title text-center mb-4">Регистрация</h3>

                        <form action="/auth/register" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>

                            <div class="mb-3">
                                <label for="name" class="form-label">Имя</label>
                                <@spring.bind "user.name" />
                                <input type="text" name="name" id="name"
                                       value="${spring.status.value!}" class="form-control <#if spring.status.error??>is-invalid</#if>">
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="surname" class="form-label">Фамилия</label>
                                <@spring.bind "user.surname" />
                                <input type="text" name="surname" id="surname"
                                       value="${spring.status.value!}" class="form-control <#if spring.status.error??>is-invalid</#if>">
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <@spring.bind "user.email" />
                                <input type="email" name="email" id="email"
                                       value="${spring.status.value!}" class="form-control <#if spring.status.error??>is-invalid</#if>">
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="username" class="form-label">Имя пользователя</label>
                                <@spring.bind "user.username" />
                                <input type="text" name="username" id="username"
                                       value="${spring.status.value!}" class="form-control <#if spring.status.error??>is-invalid</#if>">
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block">${spring.status.errorMessage}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Пароль</label>
                                <@spring.bind "user.password" />
                                <input type="password" name="password" id="password"
                                       class="form-control <#if spring.status.error??>is-invalid</#if>">
                                <#if spring.status.error??>
                                    <div class="invalid-feedback d-block">${spring.status.errorMessage}</div>
                                </#if>
                                <small class="text-muted">Минимум 6 символов, буквы и цифры</small>
                            </div>

                            <button type="submit" class="btn btn-primary w-100">Зарегистрироваться</button>
                        </form>

                        <div class="text-center mt-3">
                            <small>Уже есть аккаунт? <a href="/auth/login">Войти</a></small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.page>
