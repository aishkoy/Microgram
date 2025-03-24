<#import "layout.ftlh" as l>

<@l.layout>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap">
    <link rel="stylesheet" href="/css/profile.css">
        <#assign known = SPRING_SECURITY_CONTEXT??>
        <#if known>
            <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.name>
        </#if>

        <!-- Profile Header -->
        <div class="profile-header">
            <div class="row align-items-center">
                <div class="col-md-3 text-center profile-avatar">
                    <#if user.avatar??>
                        <img src="/api/post/${user.avatar}" alt="${user.username}'s avatar">
                    <#else>
                        <div class="avatar-placeholder">
                            <i data-lucide="user" style="width: 80px; height: 80px; color: #d1d5db;"></i>
                        </div>
                    </#if>
                </div>

                <div class="col-md-9">
                    <div class="d-flex justify-content-between align-items-center flex-wrap">
                        <div>
                            <h1 class="profile-username">${user.username}</h1>
                            <#if user.name?? && user.surname??>
                                <p class="text-muted mb-0">${user.name} ${user.surname}</p>
                            </#if>
                        </div>

                        <div class="mt-2 mt-md-0">
                            <#if (authorizedUserEmail)??>
                                <#if authorizedUserEmail == user.email>
                                    <a href="/user/profile/edit" class="btn profile-action-btn profile-edit-btn">
                                        <i data-lucide="edit-3" style="width: 16px; height: 16px; margin-right: 6px;"></i> Edit Profile
                                    </a>
                                <#else>
                                    <#assign found = false>
                                    <#list followings as following>
                                        <#if following.actualUser == user.email>
                                            <#assign found = true>
                                            <form id="unfollowForm" style="display: inline-block">
                                                <button type=submit class="btn profile-action-btn profile-unfollow-btn">
                                                    <i data-lucide="user-minus" style="width: 16px; height: 16px; margin-right: 6px;"></i> Unfollow
                                                </button>
                                            </form>
                                            <form id="followForm" style="display: none">
                                                <button type="submit" id="followBtn" class="btn profile-action-btn profile-follow-btn">
                                                    <i data-lucide="user-plus" style="width: 16px; height: 16px; margin-right: 6px;"></i> Follow
                                                </button>
                                            </form>
                                        </#if>
                                    </#list>
                                    <#if !found>
                                        <form id="followForm" style="display: inline-block">
                                            <button type="submit" id="followBtn" class="btn profile-action-btn profile-follow-btn">
                                                <i data-lucide="user-plus" style="width: 16px; height: 16px; margin-right: 6px;"></i> Follow
                                            </button>
                                        </form>
                                        <form id="unfollowForm" style="display: none;">
                                            <button type=submit class="btn profile-action-btn profile-unfollow-btn">
                                                <i data-lucide="user-minus" style="width: 16px; height: 16px; margin-right: 6px;"></i> Unfollow
                                            </button>
                                        </form>
                                    </#if>
                                </#if>
                            </#if>
                        </div>
                    </div>

                    <div class="profile-stats">
                        <div class="profile-stat-item">
                            <span class="profile-stat-count">${posts?size!0}</span>
                            <span class="profile-stat-label">Posts</span>
                        </div>

                        <div class="profile-stat-item">
                            <button id="followers" class="btn p-0 border-0 bg-transparent"
                                    data-bs-toggle="modal" data-bs-target="#followers-modal">
                                <span class="profile-stat-count" id="followers-count">0</span>
                                <span class="profile-stat-label">Followers</span>
                            </button>
                        </div>

                        <div class="profile-stat-item">
                            <button id="following" class="btn p-0 border-0 bg-transparent"
                                    data-bs-toggle="modal" data-bs-target="#following-modal">
                                <span class="profile-stat-count" id="following-count">0</span>
                                <span class="profile-stat-label">Following</span>
                            </button>
                        </div>
                    </div>

                    <#if user.aboutMe??>
                        <div class="profile-bio">
                            <p>${user.aboutMe}</p>
                        </div>
                    </#if>
                </div>
            </div>
        </div>

        <!-- Gallery Section -->
        <div class="gallery-container">
            <div class="gallery-header">
                <h2>Posts</h2>
            </div>

            <#if posts?? && posts?size gt 0>
                <div class="gallery-grid">
                    <#list posts as post>
                        <div class="gallery-card">
                            <a href="/${post.id}" class="text-decoration-none">
                                <div class="gallery-image-container">
                                    <img src="/api/post/${post.image}" class="gallery-image" alt="Post image">
                                    <div class="gallery-overlay">
                                        <div class="gallery-item-info">
                                            <li id="likes" class="gallery-item-likes" data-id="${post.id}">
                                                <i data-lucide="heart" style="width: 20px; height: 20px;"></i>
                                                <span class="like-count">${post.likesNum}</span>
                                            </li>
                                            <li id="comments" class="gallery-item-comments" data-id="${post.id}">
                                                <i data-lucide="message-circle" style="width: 20px; height: 20px;"></i>
                                                <span class="comment-count">${post.commentsNum}</span>
                                            </li>
                                        </div>
                                    </div>
                                </div>
                            </a>
                            <div class="gallery-card-footer">
                                <p class="gallery-card-date mb-0">
                                    <#if post.createdAt??>
                                        <i data-lucide="calendar" style="width: 14px; height: 14px; display: inline; vertical-align: -2px;"></i>
                                        ${post.createdAt?string("dd MMM yyyy")}
                                    </#if>
                                </p>
                            </div>
                        </div>
                    </#list>
                </div>
            <#else>
                <div class="no-posts-message">
                    <i data-lucide="image" style="width: 48px; height: 48px;"></i>
                    <p>No posts yet</p>
                </div>
            </#if>
        </div>


    <div class="modal fade" id="followers-modal" tabindex="-1" aria-labelledby="followers-modal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Followers</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                </div>
            </div>
        </div>
    </div>

    <!-- Modal following -->
    <div class="modal fade" id="following-modal" tabindex="-1" aria-labelledby="following-modal" aria-hidden="true">
        <div class="modal-dialog modal-dialog-scrollable modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Following</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                </div>
            </div>
        </div>
    </div>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/profile.js"></script>
    <script src="/js/follows.js"></script>

    <script>
        window.addEventListener("load", () => {
            let followForm = document.getElementById('followForm');
            let unfollowForm = document.getElementById('unfollowForm');
            let followersCount = document.getElementById('followers-count');

            followForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                try {
                    let response = await fetch('/api/follow', {
                        method: 'POST',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(
                            {
                                followerEmail: '${authorizedUserEmail}',
                                actualUser: '${user.email}'
                            }
                        )
                    });
                    if (response.ok) {
                        followForm.style.display = 'none';
                        unfollowForm.style.display = 'inline-block';

                        const currentCount = parseInt(followersCount.textContent || '0');
                        followersCount.textContent = currentCount + 1;
                    } else {
                        throw new Error();
                    }
                } catch (e) {
                    console.log(e);
                }
            });

            unfollowForm.addEventListener('submit', async (event) => {
                event.preventDefault();
                try {
                    let response = await fetch('/api/follow/unfollow', {
                        method: 'DELETE',
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(
                            {
                                followerEmail: '${authorizedUserEmail}',
                                actualUser: '${user.email}'
                            }
                        )
                    });
                    if (response.ok) {
                        followForm.style.display = 'inline-block';
                        unfollowForm.style.display = 'none';

                        const currentCount = parseInt(followersCount.textContent || '0');
                        followersCount.textContent = Math.max(0, currentCount - 1);
                    } else {
                        throw new Error();
                    }
                } catch (e) {
                    console.log(e);
                }
            })
        })
    </script>
</@l.layout>