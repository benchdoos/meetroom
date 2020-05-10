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

                let table = document.getElementById(targetTable);

                for (let i = 0; i <= output.length; i++) {
                    let event = output[i];
                    appendEventToTable(context, event, table, i + 1);
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
    row.insertCell(0).innerHTML = "<a href=\"" + context + "event/" + event.id + "\">" + from.toLocaleString()
        + " - " + to.toLocaleString() + "</a>";

    row.insertCell(1).innerText = event.title;

    let user = event.creator;
    //todo change it to thymeleaf fragment
    row.insertCell(2).innerHTML = "<span>\n" +
        "    <a href=\"" + context + "user/" + user.username + "\" data-toggle=\"tooltip\" data-placement=\"top\"" +
        " title=\"\" data-original-title=\"" + user.firstName + " " + user.lastName + "\">" + user.firstName + " " + user.lastName + "</a>\n" +
        "</span>";
    row.insertCell(3).innerHTML = "<a href=\"" + context + "meetroom/" + event.meetingRoom.id + "\" class=\"card-link\">" + event.meetingRoom.name + "</a>";
}