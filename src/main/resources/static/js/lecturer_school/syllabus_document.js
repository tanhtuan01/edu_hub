function addSyllabusFromListModal(e){
	fromListSyllabusDocumentSideOut('Thêm đề cương', 'Đề cương có sẵn')
}

function closeModal(e){
	e.closest(".modal").classList.remove("active")
}

function addDocumentFromListModal(e){
	fromListSyllabusDocumentSideOut('Thêm tài liệu', 'Tài liệu có sẵn')
}

function fromListSyllabusDocumentSideOut(title, h1){
	document.body.style.overflow = "hidden";
	var modal = document.querySelector(".modal-sd");
	modal.style.display = 'block';
	document.querySelector(".modal-sd h1").textContent = h1
	modal.querySelector(".eh-header-title").innerHTML = title + '<a style="width: fit-content" class="btn btn-primary" onclick="closeModal(this)">Đóng <i class="fa-solid fa-xmark"></i></a>'
	setTimeout(function() {
		modal.classList.add('active');
	}, 10);
}