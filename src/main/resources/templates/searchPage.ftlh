<#import "layout.ftlh" as main>

<@main.layout>
<form class="input-group my-3 col-auto" method="POST" action="/user/search">
    <label>
        <input name="search" class="form-control" placeholder="Type username or email..." type="text">
    </label>
    <button type="submit" class="btn btn-primary">Search</button>
</form>
</@main.layout>