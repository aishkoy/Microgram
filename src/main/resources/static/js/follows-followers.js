document.addEventListener('DOMContentLoaded', function() {
    const modalHTML = `
        <div class="modal fade" id="followersModal" tabindex="-1" aria-labelledby="followersModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="followersModalLabel">Подписчики</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body" id="followersModalBody">
                        <div class="text-center">
                            <div class="spinner-border" role="status">
                                <span class="visually-hidden">Загрузка...</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    document.body.insertAdjacentHTML('beforeend', modalHTML);

    const modal = new bootstrap.Modal(document.getElementById('followersModal'));
    const modalTitle = document.getElementById('followersModalLabel');
    const modalBody = document.getElementById('followersModalBody');

    function displayUsers(users, title) {
        modalTitle.textContent = title;

        if (!users || users.length === 0) {
            modalBody.innerHTML = `
                <div class="text-center text-muted py-4">
                    <p>Список пуст</p>
                </div>
            `;
            return;
        }

        const usersHTML = users.map(user => `
            <div class="d-flex align-items-center p-3 border-bottom">
                <img src="/api/users/${user.id}/avatar" 
                     alt="${user.username}" 
                     class="rounded-circle me-3" 
                     width="50" 
                     height="50"
                     onerror="this.src='/static/avatar/default_avatar.jpg'">
                <div class="flex-grow-1">
                    <h6 class="mb-0">
                        <a href="/users/${user.id}" class="text-decoration-none text-dark">
                            ${user.username}
                        </a>
                    </h6>
                    <small class="text-muted">
                        ${user.name}${user.surname ? ' ' + user.surname : ''}
                    </small>
                </div>
            </div>
        `).join('');

        modalBody.innerHTML = usersHTML;
    }

    async function loadFollowData(userId, endpoint, title) {
        modalBody.innerHTML = `
            <div class="text-center py-4">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Загрузка...</span>
                </div>
            </div>
        `;

        try {
            const response = await fetch(`/api/users/${userId}/${endpoint}`);

            if (response.ok) {
                const users = await response.json();
                displayUsers(users, title);
            } else if (response.status === 404) {
                // Если NSE (Not Such Element) - список пуст
                displayUsers([], title);
            } else {
                throw new Error('Ошибка загрузки данных');
            }
        } catch (error) {
            console.error('Ошибка:', error);
            modalBody.innerHTML = `
                <div class="text-center text-danger py-4">
                    <p>Ошибка загрузки данных</p>
                    <small>${error.message}</small>
                </div>
            `;
        }
    }

    const userId = window.location.pathname.split('/')[2];

    const followersElement = document.querySelector('[data-stat="followers"]');
    const followingsElement = document.querySelector('[data-stat="followings"]');

    if (followersElement) {
        followersElement.style.cursor = 'pointer';
        followersElement.addEventListener('click', function() {
            loadFollowData(userId, 'followers', 'Подписчики');
            modal.show();
        });
    }

    if (followingsElement) {
        followingsElement.style.cursor = 'pointer';
        followingsElement.addEventListener('click', function() {
            loadFollowData(userId, 'followings', 'Подписки');
            modal.show();
        });
    }
});