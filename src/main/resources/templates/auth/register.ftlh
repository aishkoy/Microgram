<#import "../layout.ftlh" as layout>
<@layout.page title="" styles="" activeNav="login">
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
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>

                            <div class="mb-3">
                                <label for="surname" class="form-label">Фамилия</label>
                                <input type="text" class="form-control" id="surname" name="surname">
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                                <#if emailError??>
                                    <div class="text-danger small mt-1">${emailError}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="username" class="form-label">Имя пользователя</label>
                                <input type="text" class="form-control" id="username" name="username" required>
                                <#if usernameError??>
                                    <div class="text-danger small mt-1">${usernameError}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Пароль</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                                <small class="text-muted">Минимум 6 символов, должны быть буквы и цифры</small>
                                <#if passwordError??>
                                    <div class="text-danger small mt-1">${passwordError}</div>
                                </#if>
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