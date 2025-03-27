function setupFollowButtons(currentUserId, profileUserId) {
    const followForm = document.getElementById('followForm');
    const unfollowForm = document.getElementById('unfollowForm');

    if (!followForm || !unfollowForm) return;

    followForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('/api/follows', {
                method: 'POST',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    follower: currentUserId,
                    actualUser: profileUserId
                })
            });

            if (response.ok) {
                followForm.style.display = 'none';
                unfollowForm.style.display = 'inline-block';

                await fetchFollowCounts(profileUserId);
            }
        } catch (e) {
            console.error('Error following user:', e);
        }
    });

    unfollowForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('/api/follows', {
                method: 'DELETE',
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    follower: currentUserId,
                    actualUser: profileUserId
                })
            });

            if (response.ok) {
                followForm.style.display = 'inline-block';
                unfollowForm.style.display = 'none';

                await fetchFollowCounts(profileUserId);
            }
        } catch (e) {
            console.error('Error unfollowing user:', e);
        }
    });
}

async function fetchFollowCounts(userId) {
    try {
        const response = await fetch(`/api/follows/${userId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch follow counts');
        }

        const data = await response.json();
        document.getElementById('followers-count').textContent = data.followers || 0;
        document.getElementById('following-count').textContent = data.following || 0;

        return data;
    } catch (error) {
        console.error('Error fetching follow counts:', error);
        return null;
    }
}

async function loadFollowLists(username) {
    try {
        // Fetch followers
        const followersResponse = await fetch(`/api/users/${username}/followers`);
        if (!followersResponse.ok) {
            throw new Error('Failed to fetch followers');
        }
        const followers = await followersResponse.json();

        // Fetch following
        const followingResponse = await fetch(`/api/users/${username}/followings`);
        if (!followingResponse.ok) {
            throw new Error('Failed to fetch following');
        }
        const following = await followingResponse.json();

        // Update modals with user lists
        updateFollowersModal(followers);
        updateFollowingModal(following);

        return { followers, following };
    } catch (error) {
        console.error('Error loading follow lists:', error);
        return null;
    }
}

function updateFollowersModal(followers) {
    const modalContent = document.getElementById('followers-modal-content');
    if (!modalContent) return;

    if (followers.length === 0) {
        modalContent.innerHTML = '<p class="text-center text-gray-500 py-4">No followers yet</p>';
        return;
    }

    modalContent.innerHTML = followers.map(user => {
        const avatar = user.avatar
            ? `<img src="/api/posts/image/${user.avatar}" alt="${user.username}'s avatar" class="w-10 h-10 rounded-full object-cover">`
            : `<div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                 <i data-lucide="user" class="w-6 h-6 text-gray-400"></i>
               </div>`;

        return `
        <div class="flex items-center p-3 border-b border-gray-100 hover:bg-gray-50">
            ${avatar}
            <a href="/@${user.username}" class="ml-3 font-medium text-gray-700 hover:text-blue-500">${user.username}</a>
        </div>`;
    }).join('');

    // Initialize icons if needed
    if (typeof lucide !== 'undefined' && typeof lucide.createIcons === 'function') {
        lucide.createIcons();
    }
}

// Update following modal content
function updateFollowingModal(following) {
    const modalContent = document.getElementById('following-modal-content');
    if (!modalContent) return;

    if (following.length === 0) {
        modalContent.innerHTML = '<p class="text-center text-gray-500 py-4">Not following anyone yet</p>';
        return;
    }

    modalContent.innerHTML = following.map(user => {
        const avatar = user.avatar
            ? `<img src="/api/posts/image/${user.avatar}" alt="${user.username}'s avatar" class="w-10 h-10 rounded-full object-cover">`
            : `<div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                 <i data-lucide="user" class="w-6 h-6 text-gray-400"></i>
               </div>`;

        return `
        <div class="flex items-center p-3 border-b border-gray-100 hover:bg-gray-50">
            ${avatar}
            <a href="/@${user.username}" class="ml-3 font-medium text-gray-700 hover:text-blue-500">${user.username}</a>
        </div>`;
    }).join('');

    // Initialize icons if needed
    if (typeof lucide !== 'undefined' && typeof lucide.createIcons === 'function') {
        lucide.createIcons();
    }
}

// Show modal functions
function showFollowersModal() {
    const modal = document.getElementById('followers-modal');
    if (modal) {
        modal.classList.remove('hidden');
    }
}

function showFollowingModal() {
    const modal = document.getElementById('following-modal');
    if (modal) {
        modal.classList.remove('hidden');
    }
}

// Hide modal function
function hideModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('hidden');
    }
}

// Initialize everything related to follows
function initFollows(currentUserId, profileUserId, username) {
    // Setup follow buttons
    setupFollowButtons(currentUserId, profileUserId);

    // Fetch initial counts
    fetchFollowCounts(profileUserId);

    // Load follow lists for modals
    loadFollowLists(username);

    // Add click listeners to follow count elements
    const followersCount = document.getElementById('followers-count-container');
    if (followersCount) {
        followersCount.addEventListener('click', showFollowersModal);
    }

    const followingCount = document.getElementById('following-count-container');
    if (followingCount) {
        followingCount.addEventListener('click', showFollowingModal);
    }

    // Add click listeners to close modal buttons
    document.querySelectorAll('.modal-close').forEach(button => {
        button.addEventListener('click', () => {
            const modalId = button.getAttribute('data-modal-id');
            hideModal(modalId);
        });
    });

    // Close modal when clicking outside
    document.querySelectorAll('.modal').forEach(modal => {
        modal.addEventListener('click', (event) => {
            if (event.target === modal) {
                modal.classList.add('hidden');
            }
        });
    });
}

// Export functions for use in other files
window.followsJS = {
    setupFollowButtons,
    fetchFollowCounts,
    loadFollowLists,
    showFollowersModal,
    showFollowingModal,
    hideModal,
    initFollows
};