function checkFileExtension(input) {
	const allowedExtensions = ['doc', 'docx', 'ppt', 'pptx', 'txt', 'pdf'];
	const fileExtension = input.value.split('.').pop().toLowerCase();

	if (!allowedExtensions.includes(fileExtension)) {
		input.value = '';
		alert('File không hợp lệ, chỉ chọn các file: Word, Powerpoint, Text, Pdf');
	}
}


function beforeSubmitSearchForm(e) {
	event.preventDefault()

	var idModule = e.querySelector("#idModule").value
	var keyword = e.querySelector("input[name='keyword']").value

	if (idModule == 0 && keyword.trim().length > 0) {
		e.submit()
	}
	if (idModule != 0 && keyword.trim().length == 0) {
		e.submit()
	}
	if (idModule != 0 && keyword.trim().length != 0) {
		e.submit()
	}

}

function previewSyllabus(e) {
	/*event.preventDefault()
	
	console.log(e)

	var baseUrl = window.location.origin;

	var hrefFile = e.getAttribute('href')
	
	var urlFile = baseUrl + hrefFile
	
	var viewerUrl = 'https://docs.google.com/viewer?url=' + urlFile;
	
	window.open(viewerUrl, '_blank');*/
}


function deleteSyllabus(e) {

	event.preventDefault()

	var tr  = e.closest("tr")
	var name = tr.querySelector(".name").textContent
	var modulename = tr.querySelector(".modulename").textContent
	var href = window.location.origin + e.getAttribute("href")
	
	Swal.fire({
		title: "Xác nhận xóa?",
		text: "Bạn có chắc muốn xóa đề cương: "+ name + ".   Trong học phần: " + modulename,
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