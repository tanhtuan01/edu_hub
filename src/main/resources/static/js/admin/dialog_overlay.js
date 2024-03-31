var dialogOverlay = document.getElementById("dialogOverlay");

document.getElementById("dialog").addEventListener("click", function(e) {
	if (e.target === dialogOverlay) {
		document.getElementById("dialog").style.display = 'none'
	}
})
const dialog = document.querySelector('#dialog');
function showConfirmDeleteDialog(event) {
	event.preventDefault();

	const deleteLink = event.currentTarget;
	
	const confirmDeleteBtn = document.getElementById('confirm-delete');

	const itemIName = deleteLink.getAttribute('data-name');
	var txtMsg = document.getElementById('txt-msg');

	txtMsg.innerText = "Bạn có chắc muốn xóa yêu cầu từ: " + itemIName;

	confirmDeleteBtn.setAttribute('href', deleteLink.href);

	dialog.style.display = 'block';
}



function confirmDelete(e){
	e.preventDefault();
	const confirmDeleteBtn = document.getElementById('confirm-delete');
	const deleteUrl = confirmDeleteBtn.getAttribute('href'); 
	window.location.href = deleteUrl;
	dialog.style.display = 'none';
}

function cancelDelete(e){
	dialog.style.display = 'none';
}