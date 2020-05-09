function setTargetObjectHtmlWithTextNoEvents(targetObjectId) {
    $("#" + targetObjectId).show();
}

function getCurrentUserEvents(context, userId, noEventsAvailableId, availableEventsId) {
    let url = getApiV1Context(context) + "/event/current/" + userId;
    console.log(url, userId);

    $.ajax({
        type: 'GET',
        url: url,
        contentType: "application/json",
        success: function (output, status, xhr) {
            console.log(output);
            if (output.length > 0) {
                $("#" + noEventsAvailableId).hide();
                $("#" + availableEventsId).show();

                for (const outputKey in output) {
                    //todo add filling table
                    $("#" + availableEventsId).append(output[outputKey].title);
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