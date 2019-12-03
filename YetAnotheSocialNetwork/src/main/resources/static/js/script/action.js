const URLs = {
    likes: '/api/likes/',
    addFriend: '/api/add-friend/'
};

function likeAPost() {
    $(document).ready(function () {

        $(document.body).on('click', '#post-like', function (ev) {
            const url = URLs.likes + $(this).attr('data-post_id');

            /*$(function () {
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            });*/

            fetch(url)
                .then($(this).html('Unlike'));

            ev.preventDefault();
            return false;
        });
    });
}

function addFriend() {
    $(document).ready(function () {
        $(document.body).on('click', '.action-add-friend', function (ev) {
            const url = URLs.addFriend + $(this).attr('data-profile_id');

            fetch(url, {
                credentials: 'include',
                method: "POST",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Cache': 'no-cache'
                }
            })
                .then((response) => {
                    alert(response);
                    return response.json();
                })
                .then((data) => {
                    console.log(data);
                    alert(data);
                });

            ev.preventDefault();
            return false;
        });
    });
}




