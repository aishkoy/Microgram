<#import "../layout.ftlh" as main>

<@main.layout>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <#if known>
        <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.name>
    </#if>

    <div class="col col-sm-10 col-md-9 col-lg-7 col-xl-6 mx-auto my-5">
        <form class="input-group my-3 col-auto" method="POST" action="/user/search">
            <label>
                <input name="search" class="form-control" placeholder="Type username or email..." type="text">
            </label>
            <button type="submit" class="btn btn-primary">Search</button>
        </form>
        <#if posts??>
            <#if posts?size != 0>
                <div class="row row-cols-1 my-2 gap-2">
                    <#list posts as post>
                        <div class="col">
                            <div class="card">
                                <div class="card-header bg-white d-flex flex-nowrap flex-row column-gap-2 align-items-center">
                                    <#if post.owner.avatar??>
                                        <img alt="${post.owner.username}" class="rounded-circle"
                                             src="api/post/${post.owner.avatar}"
                                             height="40px">
                                    <#else >
                                        <img alt="${post.owner.username}" class="rounded-circle"
                                             src="https://images.squarespace-cdn.com/content/v1/54b7b93ce4b0a3e130d5d232/1519987020970-8IQ7F6Z61LLBCX85A65S/icon.png?format=500w"
                                             height="40px">
                                    </#if>
                                    <h6 class="m-0"><a href="user/profile/${post.owner.username}">${post.owner.username}</a>
                                    </h6>
                                    <span class="text-body-secondary ms-auto align-self-end">${post.postedTime}</span>
                                </div>
                                <img src="/api/post/${post.image}"
                                     class="card-img-top" onclick="window.location.href ='/${post.id}'"
                                     alt="${post.content}">
                                <div class="card-body">
                                    <div id="actions" class="row">
                                        <h3 onclick="like(event)" class="col-auto px-2"><i class="bi bi-heart" id="${authorizedUserEmail}/${post.id}"></i></h3>

                                        <h3 class="col-auto px-2"><i class="bi bi-chat d-flex mb-auto"
                                                                     style="margin-top: 2px;"></i></h3>
                                    </div>
                                    <#if post.likesNum == 1>
                                        <div class="card-text fw-bold">1 like</div>
                                    </#if>
                                    <#if post.likesNum gt 1>
                                        <div class="card-text fw-bold">${post.likesNum} like</div>
                                    </#if>
                                    <p class="card-text">
                                        <span class="fw-semibold">${post.owner.username}</span>
                                        ${post.content}
                                    </p>
                                    <a href="#<#--TODO комменты-->" class="card-text text-body-secondary"
                                       style="text-decoration: none; display: inline-block">
                                        View comments
                                    </a>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <h5 class="my-5">Nothing new...</h5>
            </#if>
        <#else>
            <h5 class="my-5"><a href="/register">Log in</a> to see what people in your following are posting.</h5>
        </#if>
    </div>

    <script>
        window.onload = async () => {
            let likes = document.getElementsByClassName('bi-heart');
            for (let like of likes) {
                try {
                    let likeRequest = await fetch ('http://localhost:9880/api/like/isLiked')
                    if (likeRequest.ok) {
                        fillLikeBtn(like);
                    } else {
                        throw new Error();
                    }
                } catch (e) {
                    console.log (e);
                }
            }
        }


        function fillLikeBtn (heartIcon) {
            if (!heartIcon.classList.contains('text-danger')) {
                heartIcon.classList.replace('bi-heart', 'bi-heart-fill');
                heartIcon.classList.add('text-danger');
            } else {
                heartIcon.classList.replace('bi-heart-fill', 'bi-heart');
                heartIcon.classList.remove('text-danger');
            }
        }

        async function like(event) {
            event.preventDefault();
            console.log(event.target.id)
            let id = event.target.id;
            let idarr = id.split('/')
            let authorizedUserEmail = idarr[0];
            let postId = idarr[1];
            console.log('email = ' + authorizedUserEmail + ' postId' + postId);

            try {
                let request = await fetch('/api/like/like', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        likerEmail: authorizedUserEmail,
                        postId: postId
                    })
                });

                if (request.ok) {
                    fillLikeBtn(event.target);
                } else {
                    throw new Error();
                }
            } catch (e) {
                console.log(e)
            }
        }
    </script>
</@main.layout>