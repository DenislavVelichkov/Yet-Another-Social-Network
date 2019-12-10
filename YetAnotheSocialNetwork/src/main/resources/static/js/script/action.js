const URLs = {
    likes: '/api/likes',
    addFriend: '/api/add-friend',
    acceptFriend: '/api/accept-friend',
    createPhotoAlbum: '/api/create-album',
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
                body: 'likePostId' + '=' + postId
                    + '&'
                    + '_csrf' + '=' + csrfToken

            }).then($(this).toggle(function () {
                if ($(this).val() === 'Like') {
                    $(this).html('Unlike');
                } else {
                    $(this).html('Like');
                }
            }));

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

            }).then(() => window.location = '/profile/guest/' + profileId);

            ev.preventDefault();
            return false;
        });

    });
};

const acceptFriend = function () {
    $(document).ready(function () {
        $(document.body).on('click', '#accept-friend', function (ev) {
            const url = URLs.acceptFriend;
            const senderId = $(this).attr('senderId');

            fetch(url, {
                credentials: 'same-origin',
                method: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'senderId' + '=' + senderId + '&' + '_csrf' + '=' + csrfToken

            }).then(() => window.location = '/profile/guest/' + senderId);

            ev.preventDefault();
            return false;
        });
    });
};

$(document).ready(function () {
    const url = URLs.createPhotoAlbum;

    $("#photos").fileinput({
        showUpload: true,
        previewFileType: "image",
        theme: "fa",
        allowedFileExtensions: ["jpg", "jpeg", "png"],
        msgInvalidFileExtension: 'Incorrect file type for {name}, please upload one of the following file types: {extensions}',
        uploadAsync: true,
        uploadUrl: url,
        msgPlaceholder: 'Select image {files}...',
        maxTotalFileCount: 10,
        uploadExtraData: function () {
            return {
                profileId: $("input[name='profileId']").val(),
                albumName: $("input[name='albumName']").val(),
                '_csrf': csrfToken,
            };
        }
    });
});

