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
    /*Create Album func*/
    $("#photos").fileinput({
        'showUpload':true,
        'previewFileType':'any',
        'theme':'fa',
    });
});

