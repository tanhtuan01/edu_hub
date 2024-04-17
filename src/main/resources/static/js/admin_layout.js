function userShow() {
	var userShow = document.getElementById("user-show");
	userShow.classList.toggle("user-show-active");
}

function toggleMenu() {
	var leftContent = document.getElementById("leftContent");

	leftContent.classList.toggle("toggle");
}

function fullScreen() {
	if (!document.fullscreenElement) {
		if (document.documentElement.requestFullscreen) {
			document.documentElement.requestFullscreen();
		} else if (document.documentElement.mozRequestFullScreen) {
			document.documentElement.mozRequestFullScreen();
		} else if (document.documentElement.webkitRequestFullscreen) {
			document.documentElement.webkitRequestFullscreen();
		} else if (document.documentElement.msRequestFullscreen) {
			document.documentElement.msRequestFullscreen();
		}

	} else {
		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		} else if (document.msExitFullscreen) {
			document.msExitFullscreen();
		}

	}
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


window.onload = () => {
	/*removeParamatersOnUrl()*/
	showLoadingSpinner()
	setTimeout(()=>{
		hideLoadingSpinner()
	}, 500)
}