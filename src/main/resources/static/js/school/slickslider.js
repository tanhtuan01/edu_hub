createSlickSlider('.row-tp', 1, 5, 2)


function createSlickSlider(selector, slidesToShow, slidesPerRow, rows) {
        if(!document.querySelector(`${selector}`)){
            return
        }
 
    $(document).ready(function() {
        $(`${selector}`).slick({
            slidesToShow: `${slidesToShow}`,
            rows: `${rows}`,
            slidesPerRow: `${slidesPerRow}`,
            lazyLoad: 'ondemand',
            dots: true,
            prevArrow: '<button class="btn-slick-prev pointer"><i class="fa-solid fa-circle-chevron-right"></i></button>',
            nextArrow: '<button class="btn-slick-next pointer"><i class="fa-solid fa-circle-chevron-left"></i></button>'

        });
    });
}