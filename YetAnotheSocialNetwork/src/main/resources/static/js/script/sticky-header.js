window.onscroll = function () {
    myFunction();
};

function myFunction() {
    let navbar = document.querySelector('header');
    let sticky = navbar.offsetTop;

    if (window.pageYOffset >= sticky) {
        navbar.classList.add('sticky')
    } else {
        navbar.classList.remove('sticky');
    }
}


