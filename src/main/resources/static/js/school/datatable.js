if(document.getElementById("tableListTp")){
	let tableListTp = new DataTable('#tableListTp');

$('#tableListTp').DataTable().destroy();

$(document).ready(function() {
    $('#tableListTp').DataTable({
        language: {
            "sEmptyTable": "Không có dữ liệu",
            "sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
            "lengthMenu": "Hiển thị _MENU_ chương trình",
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
            { "orderable": false, "targets": [0,1,2,3,4] } // Thay [0, 1, 2] bằng các chỉ mục của các cột mà bạn muốn tắt sorting
        ],
    
    });
});

}
if(document.getElementById("facultyAssignment")){
	
let facultyAssignment = new DataTable('#facultyAssignment');

$('#facultyAssignment').DataTable().destroy();

$(document).ready(function() {
    $('#facultyAssignment').DataTable({
        language: {
            "sEmptyTable": "Không có dữ liệu",
            "sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
            "lengthMenu": "Hiển thị _MENU_ mục",
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
        lengthMenu: [15],
        columnDefs: [
            { "orderable": false, "targets": [0] } // Thay [0, 1, 2] bằng các chỉ mục của các cột mà bạn muốn tắt sorting
        ],
    
    });
});


}