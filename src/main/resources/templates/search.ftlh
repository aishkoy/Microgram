<#import "layout.ftlh" as main>

<@main.layout>
    <div class="row row-cols-1 col col-sm-9 col-md-8 col-lg-7 col-xl-6 my-5">
        <h2>Search results:</h2>

        <#if results?size != 0>
            <#list results as user>
                <div class="col">
                    <a style="display: block" href="/user/profile/${user.username}" class="card rounded-0 border-top-0 border-start-0 border-end-0">
                        <div class="card-body">
                            <div class="card-title p-0 m-0 fw-semibold">
                                <img alt="${user.username}"
                                     class="rounded-circle"
                                     src="https://images.squarespace-cdn.com/content/v1/54b7b93ce4b0a3e130d5d232/1519987020970-8IQ7F6Z61LLBCX85A65S/icon.png?format=500w"
                                     height="30px">
                                ${user.username}
                            </div>
                            <div class="card-text">${user.name}</div>
                        </div>
                    </a>
                </div>
            </#list>
        </#if>
    </div>

    <style>
        .card:hover {
            background-color: aliceblue;
        }
        a {
            text-decoration: none;
            color: black;
        }

    </style>
</@main.layout>