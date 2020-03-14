function fixPageLink() {
    const pageLinkClassName = "page-link";

    let elementsByClassName = document.getElementsByClassName("page-item");

    for (let i = 0; i < elementsByClassName.length; i++) {
        let rootClassElement = elementsByClassName[i];
        let linkElements = rootClassElement.getElementsByTagName("a");

        for (let j = 0; j < linkElements.length; j++) {
            let element = linkElements[j];
            if (!hasClass(element, pageLinkClassName)) {
                element.classList.add(pageLinkClassName);
            }
        }
    }
}

function hasClass(element, className) {
    return (' ' + element.className + ' ').indexOf(' ' + className + ' ') > -1;
}

function fixPaginationControlIcons() {
    //maybe not the best solution, but I'm not a front-ender so...
    $(".page-item:first-child .page-link span").html("<i class=\"fas fa-angle-double-left\"></i>");
    $(".page-item:last-child .page-link span").html("<i class=\"fas fa-angle-double-right\"></i>");
    $(".page-item:nth-child(2) .page-link span").html("<i class=\"fas fa-angle-left\"></i>");
    $(".page-item:nth-last-child(2) .page-link span").html("<i class=\"fas fa-angle-right\"></i>");
}

$(document).ready(function () {
    fixPageLink();
    fixPaginationControlIcons();
});
