<#import "../layout.ftlh" as layout>

<@layout.page title="Просмотр поста" styles="/static/css/post.css" activeNav="">
    <div class="container py-4">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="card shadow post-card">
                    <div class="row g-0">
                        <div class="col-md-8 post-image-container">
                            <img src="/api/posts/${post.id}/image" class="img-fluid rounded-start post-image" alt="Изображение поста">
                        </div>

                        <div class="col-md-4 d-flex flex-column">
                            <div class="card-header bg-white border-bottom d-flex align-items-center p-3">
                                <a href="/profile/${post.user.id}" class="text-decoration-none">
                                    <img src="/api/users/${post.user.id}/avatar" class="rounded-circle me-2" width="32" height="32"
                                         alt="Аватар пользователя" onerror="this.src='/static/avatar/default_avatar.jpg'">
                                    <span class="fw-bold">${post.user.username}</span>
                                </a>

                                <#if currentUser.id == post.user.id>
                                    <div class="ms-auto dropdown">
                                        <button class="btn btn-sm text-secondary" type="button" id="postOptionsDropdown"
                                                data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="bi bi-three-dots"></i>
                                        </button>
                                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="postOptionsDropdown">
                                            <li>
                                                <form action="/posts/${post.id}" method="post" class="delete-post-form">
                                                    <input type="hidden" name="_method" value="DELETE">
                                                    <button type="submit" class="dropdown-item text-danger">Удалить пост</button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </#if>
                            </div>

                            <div class="p-3 border-bottom">
                                <p class="mb-0">${post.description!""}</p>
                                <small class="text-muted">${post.createdAt?datetime}</small>
                            </div>

                            <div class="comments-container flex-grow-1 overflow-auto p-0">
                                <div class="p-3 border-bottom">
                                    <div class="d-flex align-items-center">
                                        <button class="btn btn-sm btn-link p-0 like-button" data-post-id="${post.id}">
                                            <i class="bi <#if isLiked>bi-heart-fill text-danger<#else>bi-heart</#if> fs-5"></i>
                                        </button>
                                        <span class="ms-2 likes-count">${post.likes}</span> likes
                                    </div>
                                </div>

                                <div class="comments-list p-3">
                                    <#if comments?? && comments?size gt 0>
                                        <#list comments as comment>
                                            <div class="comment mb-3" data-comment-id="${comment.id}">
                                                <div class="d-flex">
                                                    <a href="/profile/${comment.user.id}" class="text-decoration-none me-2">
                                                        <img src="/api/users/${comment.user.id}/avatar" class="rounded-circle" width="24" height="24"
                                                             alt="Аватар" onerror="this.src='/static/avatar/default_avatar.jpg'">
                                                    </a>
                                                    <div>
                                                        <div class="comment-text">
                                                            <a href="/profile/${comment.user.id}" class="fw-bold text-decoration-none me-2">
                                                                ${comment.user.username}
                                                            </a>
                                                            ${comment.content}
                                                        </div>
                                                        <small class="text-muted">${comment.createdAt}</small>
                                                    </div>

                                                    <#if currentUser.id == comment.user.id>
                                                        <div class="ms-auto">
                                                            <button class="btn btn-sm text-secondary delete-comment"
                                                                    data-comment-id="${comment.id}">
                                                                <i class="bi bi-x"></i>
                                                            </button>
                                                        </div>
                                                    </#if>
                                                </div>
                                            </div>
                                        </#list>
                                    <#else>
                                        <p class="text-muted">Нет комментариев</p>
                                    </#if>
                                </div>
                            </div>

                            <div class="p-3 border-top mt-auto">
                                <form id="comment-form" class="d-flex">
                                    <input type="text" id="comment-content" class="form-control"
                                           placeholder="Добавьте комментарий..." required>
                                    <button type="submit" class="btn btn-primary ms-2">
                                        <i class="bi bi-send"></i>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.querySelector('.like-button').addEventListener('click', function() {
                const postId = this.getAttribute('data-post-id');
                const likeIcon = this.querySelector('i');
                const likesCountEl = document.querySelector('.likes-count');

                fetch(`/posts/${postId}/like`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => response.text())
                    .then(data => {
                        if (data !== 'error') {
                            likesCountEl.textContent = data;

                            if (likeIcon.classList.contains('bi-heart')) {
                                likeIcon.classList.remove('bi-heart');
                                likeIcon.classList.add('bi-heart-fill');
                                likeIcon.classList.add('text-danger');
                            } else {
                                likeIcon.classList.remove('bi-heart-fill');
                                likeIcon.classList.remove('text-danger');
                                likeIcon.classList.add('bi-heart');
                            }
                        }
                    });
            });

            document.getElementById('comment-form').addEventListener('submit', function(e) {
                e.preventDefault();

                const content = document.getElementById('comment-content').value;
                if (!content.trim()) return;

                fetch(`/posts/${post.id}/comment`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: `content=${encodeURIComponent(content)}`
                })
                    .then(response => response.json())
                    .then(comment => {
                        const commentsList = document.querySelector('.comments-list');

                        if (commentsList.querySelector('.text-muted')) {
                            commentsList.innerHTML = '';
                        }

                        const commentElement = document.createElement('div');
                        commentElement.className = 'comment mb-3';
                        commentElement.setAttribute('data-comment-id', comment.id);
                        commentElement.innerHTML = `
                        <div class="d-flex">
                            <a href="/profile/${comment.user.id}" class="text-decoration-none me-2">
                                <img src="/api/users/${comment.user.id}/avatar" class="rounded-circle" width="24" height="24"
                                     alt="Аватар" onerror="this.src='/static/avatar/default_avatar.jpg'">
                            </a>
                            <div>
                                <div class="comment-text">
                                    <a href="/profile/${comment.user.id}" class="fw-bold text-decoration-none me-2">
                                        ${comment.user.username}
                                    </a>
                                    ${comment.content}
                                </div>
                                <small class="text-muted">${comment.createdAt}</small>
                            </div>
                            <div class="ms-auto">
                                <button class="btn btn-sm text-secondary delete-comment"
                                        data-comment-id="${comment.id}">
                                    <i class="bi bi-x"></i>
                                </button>
                            </div>
                        </div>
                    `;

                        commentsList.insertBefore(commentElement, commentsList.firstChild);
                        document.getElementById('comment-content').value = '';

                        const deleteButton = commentElement.querySelector('.delete-comment');
                        deleteButton.addEventListener('click', handleDeleteComment);
                    });
            });

            function handleDeleteComment() {
                const commentId = this.getAttribute('data-comment-id');
                const commentElement = document.querySelector(`.comment[data-comment-id="${commentId}"]`);

                if (confirm('Вы уверены, что хотите удалить этот комментарий?')) {
                    fetch(`/posts/comment/${commentId}`, {
                        method: 'DELETE'
                    })
                        .then(response => response.text())
                        .then(data => {
                            if (data.startsWith('success')) {
                                commentElement.remove();

                                const commentsList = document.querySelector('.comments-list');
                                if (commentsList.children.length === 0) {
                                    commentsList.innerHTML = '<p class="text-muted">Нет комментариев</p>';
                                }
                            }
                        });
                }
            }

            document.querySelectorAll('.delete-comment').forEach(button => {
                button.addEventListener('click', handleDeleteComment);
            });

            document.querySelector('.delete-post-form')?.addEventListener('submit', function(e) {
                if (!confirm('Вы уверены, что хотите удалить этот пост?')) {
                    e.preventDefault();
                }
            });
        });
    </script>
</@layout.page>