function addSyllabusFromListModal(e) {
	fromListSyllabusDocumentSideOut('Thêm đề cương', 'Đề cương có sẵn', 'list-syllabus')
}

function closeModal(e) {
	e.closest(".modal").classList.remove("active")
}

function addDocumentFromListModal(e) {
	fromListSyllabusDocumentSideOut('Thêm tài liệu', 'Tài liệu có sẵn', 'list-document')
}

function fromListSyllabusDocumentSideOut(title, h1, selector) {
	document.body.style.overflow = "hidden";
	var modal = document.querySelector(".modal-sd");
	modal.style.display = 'block';
	const list = modal.querySelectorAll('.list')

	list.forEach(s => {
		s.style.display = 'none'
	})
	modal.querySelector(`.list.${selector}`).style.display = 'block'
	document.querySelector(".modal-sd h1").textContent = h1
	modal.querySelector(".eh-header-title").innerHTML = title + '<a style="width: fit-content" class="btn btn-primary" onclick="closeModal(this)">Đóng <i class="fa-solid fa-xmark"></i></a>'
	setTimeout(function() {
		modal.classList.add('active');
	}, 10);
}

function checkCbBeforSubmit(e) {
	event.preventDefault();

	const cb = e.querySelectorAll("input[type='checkbox']:checked")
	if (cb.length == 0) {
		const Toast = Swal.mixin({
			toast: true,
			position: "top-end",
			showConfirmButton: false,
			timer: 3000,
			timerProgressBar: true,
			didOpen: (toast) => {
				toast.onmouseenter = Swal.stopTimer;
				toast.onmouseleave = Swal.resumeTimer;
			}
		});
		Toast.fire({
			icon: "warning",
			title: "Bạn cần chọn ít nhất 1 mục"
		});
	} else {
		e.submit()
	}
}

function deleteSyllabusOfModuleFromList(e){
	event.preventDefault()
	var baseUrl = window.location.origin;
	 
	var name = e.closest("tr").querySelector(".name").textContent
	var href = e.getAttribute("href")
	console.log(href)
	var message = `Bạn có chắc muốn xóa đề cương: ${name}`
	var url = baseUrl + href;
	alertDeleteSD(message, url)
}

function deleteDocumentOfModuleFromList(e){
	event.preventDefault()
	var baseUrl = window.location.origin;
	
	var name = e.closest("tr").querySelector(".name").textContent
	var message = `Bạn có chắc muốn xóa tài liệu: ${name}`
	var href = e.getAttribute("href")
	console.log(href)
	var url = baseUrl + href;
	alertDeleteSD(message, url)
}

function alertDeleteSD(message, href){
	Swal.fire({
  title: "Xác nhận xóa?",
  text: message,
  icon: "warning",
  showCancelButton: true,
  confirmButtonColor: "#57D45A",
  cancelButtonColor: "#d33",
  confirmButtonText: "Xóa",
  cancelButtonText: "Hủy"
}).then((result) => {
  if (result.isConfirmed) {
    window.location.href = href
  }
});
}
