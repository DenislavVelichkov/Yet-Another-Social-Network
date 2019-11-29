
    const URLs = {
        likes: '/api/likes/'
    };

    function likeAPost() {
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


}





