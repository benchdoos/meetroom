/**
 * For go back buttons and links. Goes to previous history page.
 */
function goBack() {
    window.history.back();
}

/**
 * Enable bootstrap tooltips
 */
$(document).ready(function () {
    console.log("Tooltip enabled");
    $('[data-toggle="tooltip"]').tooltip()
});

/**
 * jQuery UUID plugin 1.0.0
 *
 * @author Eugene Burtsev
 */
(function($) {
    $.uuid = function() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        });
    };
})(jQuery);


/**
 * Show validation errors on form
 *
 * @param {array} errors from spring validation
 * @param {string} formId where to find fields by names
 */
function appendValidationErrors(errors, formId) {
    if (errors.length > 0) {
        errors.forEach(error => {
            let field = error.field;
            let defaultMessage = error.defaultMessage;
            let fieldInput = $('#' + formId + ' input[name ="' + field + '"]');
            fieldInput.next().show().text(defaultMessage);
        });
    }
}