window.addEventListener("load", async function() {
    try {
        const username = document.querySelector('.profile-username').textContent;

        const responseFollower = await fetch(`/api/user/follower/${username}`);
        const responseFollowing = await fetch(`/api/user/followings/${username}`);

        if (!responseFollower.ok || !responseFollowing.ok) {
            throw new Error('Network response was not ok');
        }

        const dataFollower = await responseFollower.json();
        const dataFollowings = await responseFollowing.json();

        document.getElementById('followers-count').textContent = dataFollower.length;
        document.getElementById('following-count').textContent = dataFollowings.length;

        const followersModalBody = document.querySelector('#followers-modal .modal-body');
        followersModalBody.innerHTML = '';

        if (dataFollower.length === 0) {
            followersModalBody.innerHTML = '<p class="text-center text-muted">No followers yet</p>';
        } else {
            dataFollower.forEach(user => {
                let userAvatarHtml;
                if (user.avatar) {
                    userAvatarHtml = `<img src="/api/post/${user.avatar}" alt="${user.username}'s avatar" class="rounded-circle me-3" style="width: 40px; height: 40px; object-fit: cover;">`;
                } else {
                    userAvatarHtml = `<div class="rounded-circle me-3 bg-gray-100 flex justify-center items-center" style="width: 40px; height: 40px;">
                        <i data-lucide="user" style="width: 24px; height: 24px; color: #d1d5db;"></i>
                    </div>`;
                }

                followersModalBody.innerHTML += `
                <div class="d-flex align-items-center mb-3">
                    ${userAvatarHtml}
                    <a href="/user/profile/${user.username}" class="text-decoration-none">${user.username}</a>
                </div>
                `;
            });
        }

        const followingModalBody = document.querySelector('#following-modal .modal-body');
        followingModalBody.innerHTML = '';

        if (dataFollowings.length === 0) {
            followingModalBody.innerHTML = '<p class="text-center text-muted">Not following anyone yet</p>';
        } else {
            dataFollowings.forEach(user => {
                let userAvatarHtml;
                if (user.avatar) {
                    userAvatarHtml = `<img src="/api/post/${user.avatar}" alt="${user.username}'s avatar" class="rounded-circle me-3" style="width: 40px; height: 40px; object-fit: cover;">`;
                } else {
                    userAvatarHtml = `<div class="rounded-circle me-3 bg-gray-100 flex justify-center items-center" style="width: 40px; height: 40px;">
                        <i data-lucide="user" style="width: 24px; height: 24px; color: #d1d5db;"></i>
                    </div>`;
                }

                followingModalBody.innerHTML += `
                <div class="d-flex align-items-center mb-3">
                    ${userAvatarHtml}
                    <a href="/user/profile/${user.username}" class="text-decoration-none">${user.username}</a>
                </div>
                `;
            });
        }

        if (typeof lucide !== 'undefined' && typeof lucide.createIcons === 'function') {
            lucide.createIcons();
        }

    } catch (error) {
        console.error('Error loading follow data:', error);
    }
});