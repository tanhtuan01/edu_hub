function deleteLecturerAccount(e){
	event.preventDefault()
	
	var name = e.closest("tr").querySelector(".name").textContent
	var email = e.closest("tr").querySelector(".email").textContent
	
	var baseUrl = window.location.origin;
	var href = e.getAttribute("href")
	var url = baseUrl + href
	Swal.fire({
		title: "Xác nhận xóa?",
		text: "Bạn có chắc muốn xóa tài khoản của: " + name + "\n" + "'" + email + "'"  + " ?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#57D45A",
		cancelButtonColor: "#d33",
		confirmButtonText: "Xóa!",
		cancelButtonText: "Hủy"
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = url
		}
	});
}