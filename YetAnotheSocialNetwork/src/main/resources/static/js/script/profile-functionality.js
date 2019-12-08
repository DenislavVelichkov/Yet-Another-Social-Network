$(document).ready(function () {
    jQuery(function ($) {
        $('.profile-picture-action').hover(function () {
                $(this).find('.friend-name').show();
            }, function () {
                $(this).find('.friend-name').hide();
            }
        );
    })();
});

const friendLink = function () {
    $(document).ready(function () {
        $(document.body).on('click', '.friend-picture', function (ev) {
            window.location = '/profile/guest' + $(this).attr('value');
        });
    });
};

