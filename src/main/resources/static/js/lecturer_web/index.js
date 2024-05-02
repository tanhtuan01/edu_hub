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


function togglePaymentFields(e) {
	var courseType = document.getElementById("course-type").value;
	var paymentFields = document.getElementById("payment-fields");

	if (courseType === "paid") {
		paymentFields.classList.add("visible")
	} else {
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