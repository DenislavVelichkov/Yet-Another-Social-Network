/*Extra Post Menu functionality*/

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

let postCommentPop = function () {
    $('.yasn-post-comment').toggle();
};

/*Text Area resizer*/
/*
let observe;
if (window.attachEvent) {
    observe = function (element, event, handler) {
        element.attachEvent('on' + event, handler);
    };
} else {
    observe = function (element, event, handler) {
        element.addEventListener(event, handler, false);
    };
}

function initTextArea() {
    const text = document.querySelector('.comment-post-textarea');

    function resize() {
        text.style.height = 'auto';
        text.style.height = text.scrollHeight + 'px';
    }

    /!* 0-timeout to get the already changed text *!/
    function delayedResize() {
        window.setTimeout(resize, 0);
    }

    observe(text, 'change', resize);
    observe(text, 'cut', delayedResize);
    observe(text, 'paste', delayedResize);
    observe(text, 'drop', delayedResize);
    observe(text, 'keydown', delayedResize);

    text.focus();
    text.select();
    resize();
}*/


/*Emojies config*/
window.emojioneVersion = "3.1.2";
$(document).ready(function () {
    $("#comment-text").emojioneArea({
        buttonTitle: "Use the TAB key to insert emoji faster",
        recentEmojies: true,
        pickerPosition: "right",
        filtersPosition: "top",
        hidePickerOnBlur: true,
        search: false
    });
});

$(document).ready(function () {
    $(".comment-post-textarea").emojioneArea({
        inline: true,
        placeholder: "Type your comment here",
        buttonTitle: "Use the TAB key to insert emoji faster",
        recentEmojies: true,
        pickerPosition: "right",
        filtersPosition: "top",
        hidePickerOnBlur: true,
        search: false
    });
});


