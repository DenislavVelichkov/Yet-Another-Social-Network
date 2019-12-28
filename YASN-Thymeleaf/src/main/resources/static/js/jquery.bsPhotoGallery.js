(function ($) {
    "use strict";
    $.fn.bsPhotoGallery = function (options) {

        var settings = $.extend({}, $.fn.bsPhotoGallery.defaults, options);
        var id = generateId();
        var classesString = settings.classes;
        var classesArray = classesString.split(" ");
        var clicked = {};

        function getCurrentUl() {
            return 'ul[data-bsp-ul-id="' + clicked.ulId + '"][data-bsp-ul-index="' + clicked.ulIndex + '"]';
        }

        function generateId() {
            var ALPHABET = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
            var ID_LENGTH = 4;
            var out = '';
            for (var i = 0; i < ID_LENGTH; i++) {
                out += ALPHABET.charAt(Math.floor(Math.random() * ALPHABET.length));
            }
            return 'bsp-' + out;
        }

        function createModalWrap() {

            if ($('#bsPhotoGalleryModal').length !== 0) {
                return false;
            }

            var modal = '';
            modal += '<div class="modal fade" id="bsPhotoGalleryModal" tabindex="-1" role="dialog"';
            modal += 'aria-labelledby="myModalLabel" aria-hidden="true">';
            modal += '<div class="modal-dialog modal-xl modal-dialog-centered"><div class="modal-content">';
            modal += '<div class="modal-body"></div></div></div></div>';
            $('body').append(modal);

        }

        function showHideControls() {
            var total = $(getCurrentUl() + ' li[data-bsp-li-index]').length;

            if (total === clicked.nextImg) {
                $('a.next').hide();
            } else {
                $('a.next').show()
            }
            if (clicked.prevImg === -1) {
                $('a.previous').hide();
            } else {
                $('a.previous').show()
            }
        }

        function getSrcfromStyle(istr) {
            // return istr.replace(/url\(\"/g,'').replace(/\"\)/g,'');  //**DOESNT WORK IN SAFARI/MAC https://github.com/michaelsoriano/bootstrap-photo-gallery/issues/17
            return istr.replace('"', '')
                .replace("'", '')
                .replace('"', '')
                .replace("'", '')
                .replace('url(', '')
                .replace(')', '');
        }

        function showModal() {

            var bImgString = $(this).find('.bspImgWrapper')[0].style.backgroundImage;
            var src = getSrcfromStyle(bImgString);
            var index = $(this).attr('data-bsp-li-index');
            var ulIndex = $(this).parent('ul').attr('data-bsp-ul-index');
            var ulId = $(this).parent('ul').attr('data-bsp-ul-id');
            var pText = $(this).find('p').html();
            var modalText = typeof pText !== 'undefined' ? pText : 'undefined';


            clicked.img = src;
            clicked.prevImg = parseInt(index) - parseInt(1);
            clicked.nextImg = parseInt(index) + parseInt(1);
            clicked.ulIndex = ulIndex;
            clicked.ulId = ulId;

            $('#bsPhotoGalleryModal').modal();

            var html = '';
            var img = '<img src="' + clicked.img + '" class="img-responsive bsp-modal-main-image"/>';

            html += img;
            html += '<span class="bsp-close"><img src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiA/PjwhRE9DVFlQRSBzdmcgIFBVQkxJQyAnLS8vVzNDLy9EVEQgU1ZHIDEuMS8vRU4nICAnaHR0cDovL3d3dy53My5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkJz48c3ZnIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDEyOCAxMjgiIGhlaWdodD0iMTI4cHgiIGlkPSLQodC70L7QuV8xIiB2ZXJzaW9uPSIxLjEiIHZpZXdCb3g9IjAgMCAxMjggMTI4IiB3aWR0aD0iMTI4cHgiIHhtbDpzcGFjZT0icHJlc2VydmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPjxnPjxwb2x5Z29uIGZpbGw9IiMzNzM3MzciIHBvaW50cz0iMTIzLjU0Mjk2ODgsMTEuNTkzNzUgMTE2LjQ3NjU2MjUsNC41MTg1NTQ3IDY0LjAwMTk1MzEsNTYuOTMwNjY0MSAxMS41NTk1NzAzLDQuNDg4MjgxMyAgICAgNC40ODgyODEzLDExLjU1OTU3MDMgNTYuOTI3MjQ2MSw2My45OTcwNzAzIDQuNDU3MDMxMywxMTYuNDA1MjczNCAxMS41MjQ0MTQxLDEyMy40ODE0NDUzIDYzLjk5ODUzNTIsNzEuMDY4MzU5NCAgICAgMTE2LjQ0MjM4MjgsMTIzLjUxMTcxODggMTIzLjUxMjY5NTMsMTE2LjQ0MTQwNjMgNzEuMDczMjQyMiw2NC4wMDE5NTMxICAgIi8+PC9nPjwvc3ZnPg=="></span>';
            html += '<div class="bsp-text-container">';

            if (typeof pText !== 'undefined') {
                html += '<p class="pText">' + pText + '</p>'
            }
            html += '</div>';

            if (settings.showControl) {
                html += '<a class="bsp-controls next" data-bsp-id="' + clicked.ulId + '" href="' + (clicked.nextImg) + '">';
                html += '<i class="fas fa-arrow-circle-right"></i>';
                html += '<a class="bsp-controls previous" data-bsp-id="' + clicked.ulId + '" href="' + (clicked.prevImg) + '">';
                html += '<i class="fas fa-arrow-circle-left"></i>';
            }
            $('#bsPhotoGalleryModal .modal-body').html(html);
            $('.bsp-close').on('click', closeModal);
            showHideControls();
        }

        function closeModal() {
            $('#bsPhotoGalleryModal').modal('hide');
        }

        function nextPrevHandler() {

            var ul = $(getCurrentUl());
            var index = $(this).attr('href');

            var istr = ul.find('li[data-bsp-li-index="' + index + '"] .bspImgWrapper')[0].style.backgroundImage;
            var src = getSrcfromStyle(istr);
            var pText = ul.find('li[data-bsp-li-index="' + index + '"] p').html();
            var modalText = typeof pText !== 'undefined' ? pText : 'undefined';


            $('#bsPhotoGalleryModal .modal-body img.bsp-modal-main-image').attr('src', src);
            var txt = '';
            if (typeof pText !== 'undefined') {
                txt += '<p class="pText">' + pText + '</p>'
            }
            $('.bsp-text-container').html(txt);

            clicked.prevImg = parseInt(index) - 1;
            clicked.nextImg = parseInt(clicked.prevImg) + 2;

            if ($(this).hasClass('previous')) {
                $(this).attr('href', clicked.prevImg);
                $('a.next').attr('href', clicked.nextImg);
            } else {
                $(this).attr('href', clicked.nextImg);
                $('a.previous').attr('href', clicked.prevImg);
            }

            showHideControls();
            return false;
        }

        function clearModalContent() {
            $('#bsPhotoGalleryModal .modal-body').html('');
            clicked = {};
        }


        //START OF LOGIC//

        this.each(function (i) {
            //ul
            var items = $(this).find('li');
            $(this).attr('data-bsp-ul-id', id);
            $(this).attr('data-bsp-ul-index', i);

            items.each(function (x) {
                var theImg = $(this).find('img');
                var theText = $(this).find('p');
                var src = theImg.attr('src');

                $(this).addClass(classesString);
                $(this).attr('data-bsp-li-index', x);


                theImg.wrap('<div class="bspImgWrapper" style="background:url(\'' + src + '\');"></div>');
                theText.addClass('bspText');

                if (settings.shortText === true) {
                    theText.addClass('bspShortText');
                }

                theImg.remove();

                if (settings.hasModal === true) {
                    $(this).addClass('bspHasModal');
                    $(this).on('click', showModal);
                }
            });
        })

        if (settings.hasModal === true) {
            //this is for the next / previous buttons
            $(document).on('click', 'a.bsp-controls[data-bsp-id="' + id + '"]', nextPrevHandler);
            $(document).on('hidden.bs.modal', '#bsPhotoGalleryModal', clearModalContent);
            //start init methods
            createModalWrap();
        }

        return this;
    };
    /*defaults*/
    $.fn.bsPhotoGallery.defaults = {
        'classes': 'col-xl-2 col-lg-2 col-md-2 col-sm-4',
        'showControl': true,
        'hasModal': true,
        'shortText': true
    }


}(jQuery));
