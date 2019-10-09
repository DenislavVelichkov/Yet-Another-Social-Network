(function () {
    let alert_element =
        $(
            '<div id="bad-credentials" class="alert alert-danger">\n' +
            '<p>\n' +
            '<strong>Wrong email address or Password!</strong>\n' +
            '</p>\n' +
            '<p>\n' +
            'Please enter your credentials again.\n' +
            '</p>\n' +
            '</div>'
        )
    ;

    setTimeout(() => {
        alert_element.prependTo(document.body);

        setTimeout(() => {
            $('div#bad-credentials').remove();
        }, 5000)
    }, 100);
})();