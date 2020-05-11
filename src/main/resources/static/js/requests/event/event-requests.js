/**
 * Date time formatter already set to furmat date in format:
 * dd.MM.yyyy, HH:mm
 * @type {Intl.DateTimeFormat}
 */
const dtf = new Intl.DateTimeFormat('ru', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hc: 'h24',
        hour: '2-digit',
        minute: '2-digit'
    }
);

/**
 * Get current user's events
 *
 * @param context application context
 * @param userId user id
 * @param noEventsAvailableId id of element to show message about empty list of current events
 * @param availableEventsId table container to show
 * @param targetTable table to fill with events
 */
function getCurrentUserEvents(context, userId, noEventsAvailableId, availableEventsId, targetTable) {
    let url = getApiV1Context(context) + "/event/current/" + userId;
    console.log(url, userId);

    $.ajax({
        type: 'GET',
        url: url,
        contentType: "application/json",
        success: function (output, status, xhr) {
            if (output.length > 0) {
                $("#" + noEventsAvailableId).hide();
                $("#" + availableEventsId).show();

                let table = document.getElementById(targetTable).getElementsByTagName('tbody')[0];

                for (let i = 0; i <= output.length; i++) {
                    let event = output[i];
                    appendEventToTable(context, event, table, i);
                }
            } else {
                $("#" + availableEventsId).hide();
                $("#" + noEventsAvailableId).show();
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#" + noEventsAvailableId).show();
        }
    });
}

/**
 * Append event to given table as a row
 *
 * @param context application context
 * @param event event to append
 * @param targetTable table to append to
 * @param rowIndex row index to create new row
 */
function appendEventToTable(context, event, targetTable, rowIndex) {
    let row = targetTable.insertRow(rowIndex);

    let from = new Date(event.fromDate);
    let to = new Date(event.toDate);


    let fromFormatted = dtf.format(from);
    let toFormatted = dtf.format(to);

    row.insertCell(0).outerHTML = "<th><a href=\"" + context + "event/" + event.id + "\">"
        + fromFormatted + " - " + toFormatted
        + "</a></th>";

    row.insertCell(1).outerHTML = "<th>" + event.title + "</th>";

    let user = event.creator;

    row.insertCell(2).outerHTML = "<th>" +
        "<a href=\"" + context + "user/" + user.username + "\">"
        + user.firstName + " " + user.lastName
        + "</a></th>";
    row.insertCell(3).outerHTML = "<th>" +
        "<a href=\"" + context + "meetroom/" + event.meetingRoom.id + "\" class=\"card-link\">"
        + event.meetingRoom.name
        + "</a></th>";
}