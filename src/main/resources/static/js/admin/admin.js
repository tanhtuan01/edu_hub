/*SCHOOL*/

function changeSchoolStatus(e, status) {
	event.preventDefault()
	var str

	var tr = e.closest("tr")
	var name = tr.querySelector(".name").textContent
	var href = window.location.origin + e.getAttribute("href")

	if (status == "lock") {
		Swal.fire({
			title: "Xác nhận?",
			text: "Khóa trường " + name + "\nThao tác này sẽ khiến " + name + " tạm thời không thể hoạt động",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#57D45A",
			cancelButtonColor: "#d33",
			confirmButtonText: "Khóa",
			cancelButtonText: "Hủy"
		}).then((result) => {
			if (result.isConfirmed) {
				window.location.href = href
			}
		});
	}
	
	if (status == "unlock") {
		Swal.fire({
			title: "Xác nhận?",
			text: "Mở khóa trường " + name,
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#57D45A",
			cancelButtonColor: "#d33",
			confirmButtonText: "Mở khóa",
			cancelButtonText: "Hủy"
		}).then((result) => {
			if (result.isConfirmed) {
				window.location.href = href
			}
		});
	}
}


/*END SCHOOL*/