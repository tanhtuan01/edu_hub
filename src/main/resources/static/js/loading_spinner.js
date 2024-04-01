var opts = {
    lines: 9, // The number of lines to draw
    length: 40, // The length of each line
    width: 12, // The line thickness
    radius: 45, // The radius of the inner circle
    scale: 0.6, // Scales overall size of the spinner
    corners: 1, // Corner roundness (0..1)
    speed: 1, // Rounds per second
    rotate: 0, // The rotation offset
    animation: 'spinner-line-shrink', // The CSS animation name for the lines
    direction: 1, // 1: clockwise, -1: counterclockwise
    color: '#6DBC2F', // CSS color or array of colors
    fadeColor: 'transparent', // CSS color or array of colors
    className: 'spinner',
};

var spinner = new Spinner(opts).spin(document.getElementById("loading-spinner"));

function showLoadingSpinner() {
    document.getElementById("loading-spinner").style.display = "block";
}

function hideLoadingSpinner() {
    document.getElementById("loading-spinner").style.display = "none";
}
