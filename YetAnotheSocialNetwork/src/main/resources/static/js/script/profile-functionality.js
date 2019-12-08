$(document).ready(function () {
    $(function () {
        $('.profile-picture-action').hover(function () {
                $(this).find('.friend-name').show();
            }, function () {
                $(this).find('.friend-name').hide();
            }
        );
    });
});

