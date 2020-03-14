/**
 * Gets pageable control elements and check if any of them need `page-link` class
 */
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

/**
 * Checks if element has given class
 *
 * @param element element
 * @param className class name
 * @returns {boolean} true if element has given class
 */
function hasClass(element, className) {
    return (' ' + element.className + ' ').indexOf(' ' + className + ' ') > -1;
}

/**
 * Changes pagination control icons to FontAwesome
 */
function fixPaginationControlIcons() {
    //maybe not the best solution, but I'm not a front-ender so...
    $(".page-item:first-child .page-link span").html("<i class=\"fas fa-angle-double-left\"></i>");
    $(".page-item:last-child .page-link span").html("<i class=\"fas fa-angle-double-right\"></i>");
    $(".page-item:nth-child(2) .page-link span").html("<i class=\"fas fa-angle-left\"></i>");
    $(".page-item:nth-last-child(2) .page-link span").html("<i class=\"fas fa-angle-right\"></i>");
}

/**
 * Runs fixes for pageable control
 */
$(document).ready(function () {
    fixPageLink();
    fixPaginationControlIcons();
});
