const URLs = {
    likes: '/api/likes',
    addFriend: '/api/add-friend/',
};

function getCookie(name) {

    if (!document.cookie) {
        return null;
    }

    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith(name + '='));

    if (xsrfCookies.length === 0) {
        return null;
    }

    return decodeURIComponent(xsrfCookies[0].split('=')[1]);
}

let csrfToken = getCookie('XSRF-TOKEN');

const likeAPost = function () {
    $(document).ready(function () {

        $(document.body).on('click', '.post-like', function (ev) {
            const postId = $(this).attr('likePostId');
            const url = URLs.likes;

            fetch(url, {
                credentials: 'same-origin',
                method: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'likePostId' + '=' + postId + '&' + '_csrf' + '=' + csrfToken,

            }).then($(this).html('Unlike'));

            ev.preventDefault();
            return false;
        });

    });
};

const addFriend = function () {
    $(document).ready(function () {

        $(document.body).on('click', '.action-add-friend', function (ev) {
            const url = URLs.addFriend;
            const profileId = $(this).attr('profileId');

            fetch(url, {
                credentials: 'same-origin',
                method: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'profileId' + '=' + profileId + '&' + '_csrf' + '=' + csrfToken

            })
                .then((response) => {
                    alert(response.json());
                    return response.json();
                })
                .then((data) => {
                    console.log(data);
                    window.location = '/profile/guest/' + profileId;
                });

            ev.preventDefault();
            return false;
        });

    });
};

