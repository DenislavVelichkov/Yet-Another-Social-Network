$(document).ready(function () {
    /*Profile page sidebar functionality*/
    $(function () {
        $('.profile-picture-action').hover(function () {
                $(this).find('.friend-name').show();
            }, function () {
                $(this).find('.friend-name').hide();
            }
        );

        $('.album-picture-container').hover(function () {
                $(this).find('.album-name').show();
            }, function () {
                $(this).find('.album-name').hide();
            }
        );
    });
});

