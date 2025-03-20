let responseFollower;
let responseFollowing;
let username;

window.onload = async function () {
    username = document.getElementById("userUserName").textContent
    responseFollower = await fetch(`/api/user/follower/${username}`)
    responseFollowing = await fetch(`/api/user/followings/${username}`)

    let dataFollower = await responseFollower.json()
    let dataFollowings = await responseFollowing.json()

    const followers = document.getElementById('followers')
    const followings = document.getElementById('following')

    followers.textContent = dataFollower.length + " followers"
    followings.textContent = dataFollowings.length + " following"

    const followersModalBody = document.querySelector('#followers-modal .modal-body');
    followersModalBody.innerHTML = '';

    dataFollower.forEach(user => {
        let userAvatar = user.avatar ? '/api/post/' + user.avatar : '/defaultAvatar.jpg';
        let userUserName = user.username;
        followersModalBody.innerHTML += `
        <div class="row">
            <img class="col-4" src="${userAvatar}" style="width: 50px;" alt="userAvatar">
                <span class="col-4 text-center"><a href="/user/profile/${userUserName}">${userUserName}</a></span>
        </div>
    `;
    });

    const followingModalBody = document.querySelector('#following-modal .modal-body');
    followingModalBody.innerHTML = '';
    dataFollowings.forEach(user => {
        let userAvatar = user.avatar ? '/api/post/' + user.avatar : '/defaultAvatar.jpg';
        let userUserName = user.username;

        followingModalBody.innerHTML += `
        <div class="container px-4 text-center">
            <div class="row">
                <img class="col-4" src="${userAvatar}" style="width: 50px;" alt="userAvatar">
                <p class="col-4 text-center"><a href="/user/profile/${userUserName}">${userUserName}</a></p>
            </div>
        </div>
    `;
    });

}