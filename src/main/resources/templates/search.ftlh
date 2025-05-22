<#import "layout.ftlh" as layout>
<@layout.page title="Поиск пользователей" activeNav="search">

    <div class="container mt-4">
        <h2 class="fw-bold mb-4">Поиск пользователей</h2>

        <div class="mb-4">
            <input type="text"
                   id="user-search"
                   class="form-control"
                   placeholder="Введите имя пользователя..."
                   autocomplete="off">
        </div>

        <div id="results" class="list-group shadow-sm rounded"></div>
    </div>

    <script type="module" src="/static/js/search.js"></script>

</@layout.page>
