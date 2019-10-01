(function () {
    let alert_element = $('<div class="alert alert-danger alert-dismissable">\n' +
        '<a class="close" data-dismiss="alert" aria-label="close">×</a>\n' +
        '<strong>Wrong email address or Password!</strong> Please enter your credentials again.\n' +
        '</div>');

    alert_element.css('margin', 'auto');
    alert_element.css('padding', 'auto');
    alert_element.css('font-size', '1rem');
    alert_element.css('display', 'inline-block');
    alert_element.css('text-align', 'center');
    alert_element.css('vertical-align', 'bottom');
    alert_element.css('width', '100%');

    setTimeout(() => {
        alert_element.appendTo(document.querySelector('#login-form-container'));

        setTimeout(() => {
            $('div.alert').remove();
        }, 5000)
    }, 100);
})();