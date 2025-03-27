async function likePost(event, userId, postId) {
    event.preventDefault();

    const heartIcon = event.currentTarget.querySelector('[data-lucide="heart"]');

    try {
        const response = await fetch('/api/likes/like', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                liker: userId,
                postId: postId
            })
        });

        if (response.ok) {
            if (heartIcon.classList.contains('text-red-500')) {
                heartIcon.classList.remove('text-red-500', 'fill-red-500');
            } else {
                heartIcon.classList.add('text-red-500', 'fill-red-500');
            }
            location.reload();
        } else {
            throw new Error('Failed to like post');
        }
    } catch (error) {
        console.error('Error liking post:', error);
    }
}