
createSlickSlider()

function createSlickSlider() {
        if(!document.querySelector(".list-ltp")){
            return
        }
 	const list = document.querySelectorAll(".list-ltp .tp-item").length
 	var slidesToShow = 4;
 	var slidesPerRow = 4;
 	var rows = parseInt((list - 1) / slidesPerRow)
 	
 	if(rows > 1){
		 slidesPerRow = 1;
	 }
 	console.log(slidesToShow)
 	console.log(slidesPerRow)
 	console.log(rows)
 	
    $(document).ready(function() {
        $(".list-ltp").slick({
            slidesToShow: 4,
            rows: `${rows}`,
            slidesPerRow: 1,
            lazyLoad: 'ondemand',
            dots: true,
 
           prevArrow: '<button class="btn-slick-prev pointer"><i class="fa-solid fa-circle-chevron-left"></i></button>',
           nextArrow: '<button class="btn-slick-next pointer"><i class="fa-solid fa-circle-chevron-right"></i></button>'

        });
    });
}