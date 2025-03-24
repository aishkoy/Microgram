<#import "../layout.ftlh" as l>

<@l.layout>
    <link rel="stylesheet" href="/css/profile.css">

    <header>
        <#assign known = SPRING_SECURITY_CONTEXT??>
        <#if known>
            <#assign authorizedUserEmail = SPRING_SECURITY_CONTEXT.authentication.name>
        </#if>
        <div class="container">
            <div class="profile">
                <div class="profile-image">
                    <#if user.avatar??>
                        <img src="/api/post/${user.avatar}" style="width: 150px" alt="user avatar">
                    <#else>
                        <img src="../defaultAvatar.jpg" style="width: 150px" alt="user avatar">
                    </#if>
                </div>
                <div class="profile-user-settings">
                    <#if user.username??>
                        <h1 id="userUserName" class="profile-user-name">${user.username}</h1>
                    <#else>
                    </#if>
                    <#if (authorizedUserEmail)??>
                        <#if authorizedUserEmail == user.email>
                            <a href="/user/profile/edit" class="btn profile-edit-btn">Edit Profile</a>
                        <#else>
                            <#assign found = false>
                            <#list followings as following>
                                <#if following.actualUser == user.email>
                                    <#assign found = true>

                                    <form id="unfollowForm" style="display: inline-block">
                                        <button type=submit class="btn profile-edit-btn">unfollow</button>
                                    </form>
                                    <form id="followForm" style="display: none">
                                        <button type="submit" id="followBtn" class="btn profile-edit-btn">follow</button>
                                    </form>
                                </#if>
                            </#list>
                            <#if !found>
                                <form id="followForm" style="display: inline-block">
                                    <button type="submit" id="followBtn" class="btn profile-edit-btn">follow</button>
                                </form>
                                <form id="unfollowForm" style="display: none;">
                                    <button type=submit class="btn profile-edit-btn">unfollow</button>
                                </form>
                            </#if>
                        </#if>
                    </#if>
                </div>
                <div class="profile-stats">
                    <ul>
                        <#if posts??>
                            <li><span class="profile-stat-count">${posts?size}</span> posts</li>
                        <#else>
                            <li><span class="profile-stat-count">0</span> posts</li>
                        </#if>
                        <li>
                            <button id="followers" type="button" class="btn btn-outline-secondary profile-stat-count"
                                    data-bs-toggle="modal" data-bs-target="#followers-modal">
                            </button>
                        </li>
                        <li>
                            <button id="following" type="button" class="btn btn-outline-secondary profile-stat-count"
                                    data-bs-toggle="modal" data-bs-target="#following-modal">
                            </button>
                        </li>
                    </ul>
                </div>

                <#if user.aboutMe??>
                    <div class="profile-bio">
                        <p><span class="profile-real-name">${user.name} ${user.surname}
                            <br>
                            </span> ${user.aboutMe}</p>
                    </div>
                </#if>
            </div>
        </div>
    </header>

    <div id="modal" class="jw-modal">
        <div class="jw-modal-body">
            <div class="modal-body mx-2">
                <div class="row g-2">
                    <div id="right" class="col-md-6">Hello</div>
                    <div id="left" class="col-md-6">Dosya</div>
                </div>
            </div>
            <button onclick="closeModal()">Close</button>
        </div>
    </div>

    <div class="container">
        <div class="gallery">
            <#if posts??>
                <#list posts as post>
                    <div class="gallery-item" tabindex="0" onclick="openModal('modal',${post.id})">
                        <img src="/api/post/${post.image}"
                             class="gallery-image" alt="">
                        <div class="gallery-item-info">
                            <ul>
                                <li id="likes" class="gallery-item-likes" data-id="${post.id}">❤️<span
                                            class="visually-hidden">Likes:</span> <span class="like-count">0</span>
                                </li>
                                <li id="comments" class="gallery-item-comments" data-id="${post.id}">💬<span
                                            class="visually-hidden">Comments:</span> <span class="comment-count">0</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>

    <!-- Modal followers -->
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

    <!-- Bootstrap and other JS libraries -->
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

            followForm.addEventListener('submit', async () => {
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
                        unfollowForm.style.display = 'inline-block'
                    } else {
                        throw new Error();
                    }
                } catch (e) {
                    console.log(e);
                }
            });

            unfollowForm.addEventListener('submit', async () => {
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
                        unfollowForm.style.display = 'none'
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