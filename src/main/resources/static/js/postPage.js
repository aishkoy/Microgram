const path = window.location.pathname;
const segments = path.split('/');
const id = segments[segments.length - 1];
const commentBox = document.getElementById('commentBox');

async function fetchComment(commentId) {
    try {
        console.log(commentId)
        const response = await fetch(`/api/comment/${commentId}`)
        const comment = await response.json();
        displayComments(comment);
    } catch (error) {
        console.error('Error fetching messages:', error);
    }
}

async function sendComment() {
    const commentInput = document.getElementById('commentInput');
    const comment = commentInput.value;

    try {
        const urlFetch = await fetch(`/api/comment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                postId: id,
                content: comment,
            })
        });
        commentInput.value = '';
        if (urlFetch.ok) {
            const id = await urlFetch.json()
            console.log(id)
            await fetchComment(id);
        }
    } catch (error) {
        console.error('Error sending message:', error);
    }
}

function displayComments(comment) {
    const shouldScrollToBottom = commentBox.scrollHeight - commentBox.scrollTop === commentBox.clientHeight;

    const commentElement = document.createElement('div');
    commentElement.classList.add('mb-4');
    let commenter = comment.commenter.username || comment.commenter.email;

    commentElement.innerHTML = `
        <div id="${comment.id}" class="border border-gray-200 rounded-lg shadow-sm">
            <div class="px-4 py-2 border-b border-gray-200 font-medium bg-gray-50 rounded-t-lg">
                ${commenter}
            </div>
            <div class="px-4 py-3">
                ${comment.content}
            </div>
            <div class="px-4 py-2 border-t border-gray-200 text-sm text-gray-500 flex justify-between items-center rounded-b-lg">
                <span>${comment.commentedTime}</span>
                <button class="bg-red-50 hover:bg-red-100 text-red-700 px-3 py-1 rounded-2xl text-sm transition-colors border border-red-200 flex items-center gap-1"
                        onclick="deleteComment(${comment.id})">
                    <i data-lucide="trash-2" class="w-4 h-4"></i>
                    Delete
                </button>
            </div>
        </div>
    `;

    commentBox.appendChild(commentElement);

    if (shouldScrollToBottom) {
        commentBox.scrollTop = commentBox.scrollHeight;
    }

    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
}

async function deleteComment(id) {
    const cardWithCommentId = document.getElementById(id)
    try {
        const urlFetch = await fetch(`/api/comment/${id}`, {
            method: 'DELETE',
        });
        if (urlFetch.ok) {
            cardWithCommentId.remove()
        }else {
            console.error('Failed to delete comment:', urlFetch.statusText);
        }
    } catch (error) {
        console.error('Error deleting comment:', error);
    }
}

const sendButton = document.querySelector('.btn-send');
sendButton.addEventListener('click', sendComment);