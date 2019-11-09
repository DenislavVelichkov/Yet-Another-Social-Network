let postPicInPost = function () {
    $('.yasn-post-extra-photo').show();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-emojiies').hide();
    $('.yasn-post-extra-tag').hide();
};

let postLocationInPost = function () {
    $('.yasn-post-extra-location').show();
    $('.yasn-post-extra-emojiies').hide();
    $('.yasn-post-extra-photo').hide();
    $('.yasn-post-extra-tag').hide();
};

let postTagFriends = function () {
    $('.yasn-post-extra-tag').show();
    $('.yasn-post-extra-location').hide();
    $('.yasn-post-extra-emojiies').hide();
    $('.yasn-post-extra-photo').hide();
};