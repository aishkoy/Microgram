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
                                <a href="/users/${post.user.id}" class="text-decoration-none">
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
                                                <form action="/posts/delete/${post.id}" method="post" class="delete-post-form">
                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                                        <span class="ms-2 likes-count">${likesCount!0}</span> likes
                                    </div>
                                </div>

                                <div class="comments-list p-3">
                                    <#if comments?? && comments?size gt 0>
                                        <#list comments as comment>
                                            <div class="comment mb-3" data-comment-id="${comment.id}">
                                                <div class="d-flex">
                                                    <a href="/users/${comment.user.id}" class="text-decoration-none me-2">
                                                        <img src="/api/users/${comment.user.id}/avatar" class="rounded-circle" width="24" height="24"
                                                             alt="Аватар" onerror="this.src='/static/avatar/default_avatar.jpg'">
                                                    </a>
                                                    <div class="flex-grow-1">
                                                        <div class="comment-text">
                                                            <a href="/users/${comment.user.id}" class="fw-bold text-decoration-none me-2">
                                                                ${comment.user.username}
                                                            </a>
                                                            ${comment.content}
                                                        </div>
                                                        <small class="text-muted">${comment.createdAt}</small>
                                                    </div>

                                                    <#if currentUser.id == comment.user.id || currentUser.id == post.user.id>
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
                                        <p class="text-muted no-comments-message">Нет комментариев</p>
                                    </#if>
                                </div>
                            </div>

                            <div class="p-3 border-top mt-auto">
                                <form id="comment-form" class="d-flex">
                                    <input type="text" id="comment-content" class="form-control"
                                           placeholder="Добавьте комментарий...">
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

    <div style="display: none;">
        <span id="current-user-data" data-user-id="${currentUser.id}" data-username="${currentUser.username}"></span>
        <span id="post-data" data-post-id="${post.id}" data-owner-id="${post.user.id}"></span>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const likeButton = document.querySelector('.like-button');
            const likesCountElement = document.querySelector('.likes-count');

            if (likeButton) {
                likeButton.addEventListener('click', function() {
                    const postId = this.getAttribute('data-post-id');

                    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
                    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

                    fetch('/api/posts/' + postId + '/like', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            [csrfHeader]: csrfToken
                        }
                    })
                        .then(response => response.json())
                        .then(data => {
                            const heartIcon = this.querySelector('i');

                            if (data.isLiked) {
                                heartIcon.className = 'bi bi-heart-fill text-danger fs-5';
                            } else {
                                heartIcon.className = 'bi bi-heart fs-5';
                            }

                            likesCountElement.textContent = data.likesCount;
                        })
                        .catch(error => {
                            console.error('Ошибка при обработке лайка:', error);
                        });
                });
            }

            const commentForm = document.getElementById('comment-form');
            const commentsList = document.querySelector('.comments-list');
            const noCommentsMessage = document.querySelector('.no-comments-message');

            if (commentForm) {
                commentForm.addEventListener('submit', function(e) {
                    e.preventDefault();

                    const commentInput = document.getElementById('comment-content');
                    const content = commentInput.value.trim();

                    if (!content) {
                        alert('Комментарий не может быть пустым');
                        return;
                    }

                    const postId = getPostId();
                    const submitButton = this.querySelector('button[type="submit"]');

                    submitButton.disabled = true;

                    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
                    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

                    fetch('/api/comments/posts/' + postId, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            [csrfHeader]: csrfToken
                        },
                        body: JSON.stringify({ content: content })
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Ошибка сервера: ' + response.status);
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.error) {
                                alert('Ошибка: ' + data.error);
                                return;
                            }

                            if (noCommentsMessage) {
                                noCommentsMessage.style.display = 'none';
                            }
                            addCommentToList(data, true);

                            commentInput.value = '';
                        })
                        .catch(error => {
                            console.error('Ошибка при добавлении комментария:', error);
                            alert('Ошибка при добавлении комментария');
                        })
                        .finally(() => {
                            submitButton.disabled = false;
                        });
                });
            }

            document.addEventListener('click', function(e) {
                if (e.target.classList.contains('delete-comment') || e.target.parentElement.classList.contains('delete-comment')) {
                    e.preventDefault();

                    const button = e.target.classList.contains('delete-comment') ? e.target : e.target.parentElement;
                    const commentId = button.getAttribute('data-comment-id');

                    if (confirm('Вы уверены, что хотите удалить этот комментарий?')) {
                        deleteComment(commentId);
                    }
                }
            });

            const deletePostForm = document.querySelector('.delete-post-form');
            if (deletePostForm) {
                deletePostForm.addEventListener('submit', function(e) {
                    if (!confirm('Вы уверены, что хотите удалить этот пост?')) {
                        e.preventDefault();
                    }
                });
            }
        });

        function getPostId() {
            const postData = document.getElementById('post-data');
            return postData.getAttribute('data-post-id');
        }

        function getCurrentUser() {
            const userData = document.getElementById('current-user-data');
            return {
                id: parseInt(userData.getAttribute('data-user-id')),
                username: userData.getAttribute('data-username')
            };
        }

        function isPostOwner() {
            const currentUser = getCurrentUser();
            const postData = document.getElementById('post-data');
            const postOwnerId = parseInt(postData.getAttribute('data-owner-id'));

            return currentUser.id === postOwnerId;
        }

        function addCommentToList(comment, prepend) {
            if (prepend === undefined) prepend = false;

            const commentsList = document.querySelector('.comments-list');
            const currentUser = getCurrentUser();

            const commentElement = document.createElement('div');
            commentElement.className = 'comment mb-3';
            commentElement.setAttribute('data-comment-id', comment.id);

            const canDelete = currentUser.id === comment.user.id || isPostOwner();

            const deleteButtonHtml = canDelete ?
                '<div class="ms-auto">' +
                '<button class="btn btn-sm text-secondary delete-comment" data-comment-id="' + comment.id + '">' +
                '<i class="bi bi-x"></i>' +
                '</button>' +
                '</div>' : '';

            commentElement.innerHTML =
                '<div class="d-flex">' +
                '<a href="/users/' + comment.user.id + '" class="text-decoration-none me-2">' +
                '<img src="/api/users/' + comment.user.id + '/avatar" class="rounded-circle" width="24" height="24"' +
                ' alt="Аватар" onerror="this.src=\'/static/avatar/default_avatar.jpg\'">' +
                '</a>' +
                '<div class="flex-grow-1">' +
                '<div class="comment-text">' +
                '<a href="/users/' + comment.user.id + '" class="fw-bold text-decoration-none me-2">' +
                comment.user.username +
                '</a>' +
                comment.content +
                '</div>' +
                '<small class="text-muted">только что</small>' +
                '</div>' +
                deleteButtonHtml +
                '</div>';

            if (prepend) {
                commentsList.insertBefore(commentElement, commentsList.firstChild);
            } else {
                commentsList.appendChild(commentElement);
            }
        }

        function deleteComment(commentId) {
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            fetch('/api/comments/' + commentId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка сервера: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.error) {
                        alert('Ошибка: ' + data.error);
                        return;
                    }

                    const commentElement = document.querySelector('[data-comment-id="' + commentId + '"]');
                    if (commentElement) {
                        commentElement.remove();
                    }

                    const remainingComments = document.querySelectorAll('.comment');
                    const noCommentsMessage = document.querySelector('.no-comments-message');
                    if (remainingComments.length === 0 && noCommentsMessage) {
                        noCommentsMessage.style.display = 'block';
                    }
                })
                .catch(error => {
                    console.error('Ошибка при удалении комментария:', error);
                    alert('Ошибка при удалении комментария');
                });
        }
    </script>
</@layout.page>