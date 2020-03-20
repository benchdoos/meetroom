function goBack() {
    window.history.back();
}

$(document).ready(function () {
    console.log("Tooltip enabled");
    $('[data-toggle="tooltip"]').tooltip()
});