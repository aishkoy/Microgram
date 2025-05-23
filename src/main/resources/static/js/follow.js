document.addEventListener("DOMContentLoaded", function () {
    const followBtn = document.getElementById("followBtn");

    if (!followBtn) return;

    const userId = followBtn.dataset.userId;

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch(`/api/follows/${userId}`)
        .then(res => res.json())
        .then(isFollowing => {
            updateButton(isFollowing);
        });

    function updateButton(isFollowing) {
        followBtn.textContent = isFollowing ? 'Отписаться' : 'Подписаться';
        followBtn.className = `btn btn-sm btn-${isFollowing ? 'secondary' : 'primary'}`;
        followBtn.dataset.following = isFollowing;
    }

    followBtn.addEventListener("click", () => {
        const isFollowing = followBtn.dataset.following === "true";

        fetch(`/api/users/${userId}/follow?isFollow=${isFollowing}`, {
            method: "POST",
            headers: {
                [csrfHeader]: csrfToken
            }
        }).then(res => {
            if (res.ok) {
                updateButton(!isFollowing);
                location.reload();
            } else {
                alert("Ошибка при попытке изменить подписку.");
            }
        });
    });
});

