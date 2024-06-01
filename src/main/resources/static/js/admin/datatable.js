if (document.getElementById("SchoolOnWeb")) {
	let SchoolOnWeb = new DataTable('#SchoolOnWeb');

	$('#SchoolOnWeb').DataTable().destroy();

	$(document).ready(function() {
		$('#SchoolOnWeb').DataTable({
			language: {
				"sEmptyTable": "Không có dữ liệu",
				"sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
				"lengthMenu": "Hiển thị _MENU_ trường",
				"emptyTable": "Không có dữ liệu phù hợp",
				"zeroRecords": "Không tìm thấy dữ liệu phù hợp",
				"infoEmpty": "0 / ",
				"infoFiltered": "_MAX_",
				"search": "Tìm kiếm",
				"paginate": {
					"previous": "Trước",
					"next": "Tiếp"
				},
			},
			lengthMenu: [15, 25, 35],
			columnDefs: [
				{ "orderable": false, "targets": [ 1, 2, 3, 4, 5, 7] } // Thay [0, 1, 2] bằng các chỉ mục của các cột mà bạn muốn tắt sorting
			],

		});
	});

}


if (document.getElementById("StudentUser")) {
	let StudentUser = new DataTable('#StudentUser');

	$('#StudentUser').DataTable().destroy();

	$(document).ready(function() {
		$('#StudentUser').DataTable({
			language: {
				"sEmptyTable": "Không có dữ liệu",
				"sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
				"lengthMenu": "Hiển thị _MENU_ tài khoản",
				"emptyTable": "Không có dữ liệu phù hợp",
				"zeroRecords": "Không tìm thấy dữ liệu phù hợp",
				"infoEmpty": "0 / ",
				"infoFiltered": "_MAX_",
				"search": "Tìm kiếm",
				"paginate": {
					"previous": "Trước",
					"next": "Tiếp"
				},
			},
			lengthMenu: [15, 25, 35, 50],
			columnDefs: [
				{ "orderable": false, "targets": [ 1, 2] } // Thay [0, 1, 2] bằng các chỉ mục của các cột mà bạn muốn tắt sorting
			],

		});
	});

}