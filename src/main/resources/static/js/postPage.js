const path = window.location.pathname;
const segments = path.split('/');
const id = segments[segments.length - 1];
const commentBox = document.getElementById('commentBox');

async function fetchComment(commentId) {
    try {
        console.log(commentId)
        const response = await fetch(`/api/comment/last/${commentId}`)
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
    commentElement.classList.add('col-12', 'mb-3');
    let commenter = comment.commenter.username || comment.commenter.email
    commentElement.innerHTML = `
        <div id="${comment.id}" class="card d-flex">
                                        <div class="card-header">
                                                ${commenter}
                                        </div>
                                        <div class="card-body">
                                            ${comment.content}
                                        </div>
                                        <div class="card-footer">
                                            ${comment.commentedTime}
                                            <button class="btn btn-danger fa-align-right" onclick="deleteComment(${comment.id})" style="width: 200px">
                                                delete comment
                                            </button>
                                        </div>
                                    </div>
        `
    commentBox.appendChild(commentElement)

    if (shouldScrollToBottom) {
        commentBox.scrollTop = commentBox.scrollHeight;
    }
}

async function deleteComment(id) {
    const cardWithCommentId = document.getElementById(id)
    try {
        const urlFetch = await fetch(`/api/comment/delete/${id}`, {
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