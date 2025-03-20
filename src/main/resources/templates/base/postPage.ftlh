<#import "../layout.ftlh" as main>

<@main.layout>

    <div class="container my-5">
        <div class="row g-2">
            <div class="col-6 border bg-light" style="height: 800px; overflow-y: auto;">
                <div class="p-3 d-flex flex-column">
                    <div class="card flex-grow-1">
                        <div class="card-header">
                            ${post.owner.username}
                        </div>
                        <div class="card-body">
                            <img src="/api/post/${post.image}" style="width: 500px; height: auto" alt="${post.content}">
                        </div>
                        <div class="card-footer">
                            <p class="text-bold">${post.content}</p>
                            <div id="actions" class="row">
                                <h3 onclick="likePost()" class="col-auto px-2"><i
                                            class="bi bi-heart bi-heart-fill text-danger"></i></h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-6 border bg-light d-flex flex-column" style="height: 800px; overflow-y: auto;">
                <div class="p-3">
                    <div id="commentBox" class="row flex-grow-1">
                        <#if comments??>
                            <#list comments as comment>
                                <div class="col-12 mb-3">
                                    <div id="${comment.id}" class="card d-flex">
                                        <div class="card-header">
                                            <#if comment.commenter.username??>
                                                ${comment.commenter.username}
                                            <#else >
                                                ${comment.commenter.email}
                                            </#if>
                                        </div>
                                        <div class="card-body">
                                            ${comment.content}
                                        </div>
                                        <div class="card-footer">
                                            ${comment.commentedTime.getDayOfMonth()}
                                            -${comment.commentedTime.getMonth()} ${comment.commentedTime.getHour()}
                                            :${comment.commentedTime.getMinute()}
                                            <#if authEmail == comment.commenter.email>
                                                <button class="btn btn-danger fa-align-right"
                                                        onclick="deleteComment(${comment.id})" style="width: 200px">
                                                    delete comment
                                                </button>
                                            </#if>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </div>
                <div class="chat-input">
                    <input type="text" id="commentInput" placeholder="Type a comment...">
                    <button class="btn btn-primary btn-send" type="submit">Send</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
    <script src="/js/postPage.js/"></script>
    <script>
        let lastCommentNum = ${comments?size}

        function likePost(event) {
            event.preventDefault();
            const heartIcon = event.target;
            if (!heartIcon.classList.contains('text-danger')) {
                heartIcon.classList.replace('bi-heart', 'bi-heart-fill');
                heartIcon.classList.add('text-danger');
            } else {
                heartIcon.classList.replace('bi-heart-fill', 'bi-heart');
                heartIcon.classList.remove('text-danger');
            }
        }
    </script>
</@main.layout>
