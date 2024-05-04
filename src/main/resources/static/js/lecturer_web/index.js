window.onload = () => {
	showLoadingSpinner()
	setTimeout(() => {
		hideLoadingSpinner()
	}, 500)
}


function goBack(){
	window.history.back();
}

function checkInputNumber(event) {
	var value = event.key
	stopArrowUpDown(event)
	if (value === 'e' || value === '.' || value === '-') {
		event.preventDefault()
	}
}

function stopArrowUpDown(event) {
	if (event.key === 'ArrowUp' || event.key === 'ArrowDown') {
		event.preventDefault()
	}
}

function checkInputNumberHasValue(e) {

	var inputValue = parseInt(e.value)
	var paymentFields = e.closest("#payment-fields")
	if (inputValue > 0) {
		paymentFields.classList.add("visible")
		document.getElementById("course-type").value = "paid"
	}




}

function togglePaymentFields(e) {
	var courseType = document.getElementById("course-type").value;
	var paymentFields = document.getElementById("payment-fields");

	if (courseType === "paid") {
		paymentFields.classList.add("visible")
	} else {
		var paymentFieldNumber = paymentFields.querySelectorAll("input[type='number']")
		paymentFieldNumber.forEach((s) => {
			s.value = 0
		})

		var paymentSmallField = paymentFields.querySelectorAll("small")
		paymentSmallField.forEach((s) => {
			s.textContent = ''
		})
		paymentFields.classList.remove("visible");
	}
}

function toNewPrice(e) {
	var form = e.closest("form")
	var oldPrice = parseInt(form.querySelector("#oldPrice").value)
	var discount = parseInt(form.querySelector("#discount").value)
	var newPrice = form.querySelector("#newPrice")
	var newPriceValue;

	if (!isNaN(discount) && discount > 100) {
		form.querySelector("#discountTxt").innerText = "Giá trị không hợp lệ"
		return
	}

	if (!isNaN(oldPrice) && !isNaN(discount)) {
		newPrice.value = oldPrice - oldPrice * ((discount) / 100)
		newPriceValue = newPrice.value
		form.querySelector("#oldPriceTxt").innerText = soSangChu(oldPrice)
		if (newPriceValue.includes('.')) {
			return
		}
		form.querySelector("#newPriceTxt").innerText = soSangChu(newPriceValue)
	} else if (!isNaN(oldPrice) && isNaN(discount)) {
		form.querySelector("#oldPriceTxt").innerText = soSangChu(oldPrice)
		form.querySelector("#newPriceTxt").innerText = ''
	} else if (isNaN(oldPrice) && !isNaN(discount)) {
		form.querySelector("#newPriceTxt").innerText = ''
		form.querySelector("#oldPriceTxt").innerText = ''
	} else {
		form.querySelector("#oldPriceTxt").innerText = ''
		form.querySelector("#newPriceTxt").innerText = ''
	}

}



function soSangChu(so) {

	const chuSo = ['không', 'một', 'hai', 'ba', 'bốn', 'năm', 'sáu', 'bảy', 'tám', 'chín'];
	const hang = ['', 'nghìn', 'triệu', 'tỷ', 'nghìn tỷ', 'triệu tỷ'];
	const donViTien = 'đồng';

	function docBaChuSo(baChuSo) {
		let tram, chuc, donVi;
		let ketQua = '';
		tram = parseInt(baChuSo / 100);
		chuc = parseInt((baChuSo % 100) / 10);
		donVi = baChuSo % 10;
		if (tram > 0) {
			ketQua += chuSo[tram] + ' trăm ';
			if ((chuc === 0) && (donVi > 0)) ketQua += 'linh ';
		}
		if ((chuc > 1) && (chuc !== 0)) {
			ketQua += chuSo[chuc] + ' mươi';
			if ((chuc === 0) && (donVi > 0)) ketQua = ketQua.slice(0, -1) + 'linh ';
		}
		if (chuc === 1) ketQua += 'mười';
		switch (donVi) {
			case 1:
				if ((chuc !== 0) && (chuc !== 1)) {
					ketQua += ' mốt';
				} else {
					ketQua += chuSo[donVi];
				}
				break;
			case 5:
				if (chuc === 0) {
					ketQua += chuSo[donVi];
				} else {
					ketQua += ' lăm';
				}
				break;
			default:
				if (donVi > 0) {
					ketQua += ' ' + chuSo[donVi];
				}
				break;
		}
		return ketQua;
	}

	if (so === 0) return 'không đồng';
	let viTri = 0;
	let ketQua = '';
	let phanDu = 0;
	while (so > 0) {
		phanDu = so % 1000;
		if (viTri === 0) {
			ketQua = docBaChuSo(phanDu);
		} else if (phanDu > 0) {
			ketQua = docBaChuSo(phanDu) + ' ' + hang[viTri] + ' ' + ketQua;
		}
		so = parseInt(so / 1000);
		viTri++;
	}
	return ketQua.trim() + ' ' + donViTien;
}

function addCoursesChangeImage(input) {
	const file = input.files[0];
	const imageChoosed = document.getElementById('imageChoosed');

	const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
	const maxSizeInBytes = 5 * 1024 * 1024;

	if (!allowedExtensions.exec(file.name)) {
		alert('Hãy chọn 1 tệp hình ảnh.');
		input.value = '';
		return;
	}

	if (file.size > maxSizeInBytes) {
		alert('Kích thước ảnh quá lớn, vượt quá 5MB.');
		input.value = '';
		return;
	}

	const reader = new FileReader();

	reader.addEventListener('load', function() {
		imageChoosed.src = reader.result;
	});

	if (file) {
		reader.readAsDataURL(file);
	}
}

function deleteCourses(e) {
	event.preventDefault()
	var href = e.getAttribute("href")
	var originUrl = window.location.origin
	var url = originUrl + href
	var container = e.closest(".course")
	var title = container.querySelector(".title").textContent

	Swal.fire({
		title: "Xác nhận xóa",
		text: `Bạn có chắc muốn xóa khóa học: ${title} ?`,
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "Xóa!"
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = url
		}
	});
}

function checkVideo(event) {
	var fileInput = event.target;
	var file = fileInput.files[0];

	if (file) {
		var fileName = file.name;
		var fileExtension = fileName.split('.').pop().toLowerCase();
		var fileSize = file.size;
		var maxSize = 100 * 1024 * 1024; // 50MB

		if (fileExtension !== 'mp4' && fileExtension !== 'mov' && fileExtension !== 'avi') {
			alert('Hãy chọn một tệp video (mp4, mov, avi)');
			fileInput.value = '';
		} else if (fileSize > maxSize) {
			alert('Kích thước tệp quá lớn. Vui lòng chọn tệp nhỏ hơn 100MB.');
			fileInput.value = '';
		}
	} else {
		console.log('Không có tệp được chọn.');
	}
}

function deleteLesson(e) {
	event.preventDefault()

	var lesson = e.closest(".lesson-item")
	var name = lesson.querySelector(".name").textContent
	
	
	var url = window.location.origin + e.getAttribute("href")

	Swal.fire({
		title: "Xác nhận xóa?",
		text: `Bạn có chắc muốn xóa bài học: ${name}`,
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#ff0000",
		cancelButtonColor: "#57D45A",
		confirmButtonText: "Xóa",
		cancelButtonText: "Hủy xóa"
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = url
		}
	});
}

checkOptionCourses()
function checkOptionCourses(){
	if(!document.getElementById("course-type")){
		return
	}
	var coursesTypeValue = document.getElementById("course-type").value
	if(coursesTypeValue == "paid"){
		document.getElementById("payment-fields").classList.add("visible")
	}
}
valueToPriceText()
function valueToPriceText(){
	if(!document.getElementById("course-type")){
		return
	}
	var oldPrice = parseInt(document.getElementById("oldPrice").value)
	document.getElementById("oldPriceTxt").innerText = soSangChu(oldPrice)
	var newPrice = parseInt(document.getElementById("newPrice").value)
	document.getElementById("newPriceTxt").innerText = soSangChu(newPrice)
}

