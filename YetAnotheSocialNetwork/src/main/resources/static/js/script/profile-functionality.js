/*Profile page sidebar functionality*/
$(document).ready(function () {
    $(function () {
        $('.friends-pictures-container li').hover(function () {
                $(this).find('.friend-name').show();
            }, function () {
                $(this).find('.friend-name').hide();
            }
        );

        $('.album-pictures-container li').hover(function () {
                $(this).find('.album-name').show();
            }, function () {
                $(this).find('.album-name').hide();
            }
        );
    });
});

