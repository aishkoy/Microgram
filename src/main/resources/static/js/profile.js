async function openModal(id, postId) {
    const response = await fetch(`api/post/get/` + postId)
    const dataPost = await response.json()

    let right = document.getElementById('right')
    right.innerHTML =''
    let img = document.createElement('img')
    img.setAttribute('src',"/api/post/"+dataPost.image)
    img.setAttribute('alt',dataPost.content)
    img.style.width = '500px'
    img.style.height = '600px'
    right.appendChild(img)
    let content = document.createElement('h3')
    content.textContent = dataPost.content

    let dateOfAdding = document.createElement('p')
    dateOfAdding.textContent = dataPost.postedTime;
    right.appendChild(content)
    right.appendChild(dateOfAdding)

    const responseComments = await fetch(`/api/comment/${dataPost.id}`);
    const dataComments = await responseComments.json();
    console.log(dataComments);

    let comments = document.createElement('div');

    dataComments.forEach(c => {
        let commentElement = document.createElement('p');
        commentElement.innerHTML = `${c.commenter.username}: ${c.content}<br>${c.commentedTime}`;
        comments.appendChild(commentElement);
    });

    left.appendChild(comments);
    document.getElementById(id).classList.add('open');
    document.body.classList.add('jw-modal-open');
}

function closeModal() {
    document.querySelector('.jw-modal.open').classList.remove('open');
    document.body.classList.remove('jw-modal-open');
}

window.addEventListener('load', function () {
    document.addEventListener('click', event => {
        if (event.target.classList.contains('jw-modal')) {
            closeModal();
        }
    });
});


window.onload = async function () {
    const galleryItems = document.querySelectorAll('.gallery-item');
    for (const item of galleryItems) {
        const postId = item.querySelector('.gallery-item-likes').getAttribute('data-id');
        const likeCountElement = item.querySelector('.like-count');
        const commentCountElement = item.querySelector('.comment-count');

        const likeResponse = await fetch(`/api/like/` + postId);
        const commentResponse = await fetch(`/api/comment/` + postId);
        const likeData = await likeResponse.json();
        const commentData = await commentResponse.json();
        if (likeData.length > 0) {
            likeCountElement.textContent = likeData.length;
        } else {
            likeCountElement.textContent = '0';
        }
        if (commentData.length > 0) {
            commentCountElement.textContent = commentData.length;
        } else {
            commentCountElement.textContent = '0';
        }
    }
}