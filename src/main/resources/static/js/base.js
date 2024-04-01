document.onreadystatechange = function() {
    if (document.readyState === 'loading') {
        showLoadingSpinner();
    } else {
        hideLoadingSpinner();
    }

};


function removeParamatersOnUrl() {
	var url = window.location.href;

	if (url.indexOf('?') !== -1) {
		var cleanUrl = url.substring(0, url.indexOf('?'));
		window.history.replaceState({}, document.title, cleanUrl);
	}
}

window.onload = () => {
	removeParamatersOnUrl()
}