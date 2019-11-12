let postPicInPost = function () {
    $('.yasn-post-extra-photo').show();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-tag').hide();
    $('.yasn-post-extra-privacy').hide()
};

let postLocationInPost = function () {
    $('.yasn-post-extra-location').show();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-tag').hide();
    $('.yasn-post-extra-privacy').hide()
};

let postTagFriends = function () {
    $('.yasn-post-extra-tag').show();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-privacy').hide()
};

let postPrivacyToggle = function () {
    $('.yasn-post-extra-privacy').show();
    $('.yasn-post-extra-tag').hide();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-photo').hide();
};

$(document).ready(function () {
    $("#comment-text").emojioneArea({
        buttonTitle: "Use the TAB key to insert emoji faster",
        recentEmojies: true,
        pickerPosition: "right",
        filtersPosition: "top",
        hidePickerOnBlur: false,
        spellcheck: true,
        search: false
    });
});



