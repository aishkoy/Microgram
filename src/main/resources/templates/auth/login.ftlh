<#import "../layout.ftlh" as layout>
<@layout.page title="" styles="" activeNav="login">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card mt-5">
                    <div class="card-body">
                        <h3 class="card-title text-center mb-4">Вход в MicroGram</h3>

                        <#if loginError??>
                            <div class="alert alert-danger mb-4">
                                ${loginError}
                            </div>
                        </#if>

                        <#if registrationSuccess??>
                            <div class="alert alert-success mb-4">
                                ${registrationSuccess}
                            </div>
                        </#if>

                        <form action="/login" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>
                            <div class="mb-3">
                                <label for="login" class="form-label">Email или имя пользователя</label>
                                <input type="text" class="form-control" id="login" name="login">
                                <#if loginError??>
                                    <div class="text-danger small mt-1">${loginError}</div>
                                </#if>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Пароль</label>
                                <input type="password" class="form-control" id="password" name="password">
                                <#if passwordError??>
                                    <div class="text-danger small mt-1">${passwordError}</div>
                                </#if>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 mb-3">Войти</button>
                        </form>

                        <div class="text-center mt-3">
                            <small>У вас нет аккаунта? <a href="/auth/register">Зарегистрироваться</a></small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout.page>