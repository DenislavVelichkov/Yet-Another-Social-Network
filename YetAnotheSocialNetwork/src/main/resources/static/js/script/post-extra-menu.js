let postPrivacyToggle = function () {
    $('.yasn-post-extra-privacy ul').toggle();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-tag').hide();
};

let postPicInPost = function () {
    $('.yasn-post-extra-photo').toggle();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-tag').hide();
    $('.yasn-post-extra-privacy ul').hide();
};

let postLocationInPost = function () {
    $('.yasn-post-extra-location').toggle();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-privacy ul').hide();
    $('.yasn-post-extra-tag').hide();
};

let postTagFriends = function () {
    $('.yasn-post-extra-tag').toggle();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-privacy ul').hide();
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



