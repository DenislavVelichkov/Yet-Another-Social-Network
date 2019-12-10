$(document).ready(function () {
    /*Gallery func*/
    $(function () {
        $('.profile-picture-action').hover(function () {
                $(this).find('.friend-name').show();
            }, function () {
                $(this).find('.friend-name').hide();
            }
        );
    });
});

