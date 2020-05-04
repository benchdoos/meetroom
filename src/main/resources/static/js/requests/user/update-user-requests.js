/**
 * Provides api prefix to any ajax request
 */
$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
    let context = "/meetroom"; //fixme change to context
    const API_PREFIX = "/api/v1";
    options.url = context + API_PREFIX + options.url
});


function getUserInfo(userId) {
    let url = "/user/" + userId;

    console.log("URL: ", url);

    $.ajax({
        type: 'GET',
        url: url,
        success: function (output, status, xhr) {
            console.log("Response is:", output);
            console.log("Status is:", status);
            console.log("xhr is:", xhr);
        }
    });

}

function updateUserUsername(changeUsernameDto) {
    let url = "/user/change-username";

    console.log("URL: ", url);
    console.log("DTO:", changeUsernameDto);

    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(changeUsernameDto),
        success: function (output, status, xhr) {
            console.log("Response: ", status, output);
            console.log("XHR is:", xhr);
            location.reload(); //todo fix session problem (see: meetroom-75)
        },
        failure: function (error) {
            console.log("error: ", error);
        }
    });
}

function getApiV1Context(context) {
    return context + "api/v1";
}

function updateUserAvatar(context, userId, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/by-user/" + userId;

    console.log("Requesting user avatar by url:", url);

    $.ajax({
        type: 'GET',
        url: url,
        contentType: "application/json",
        success: function (output, status, xhr) {
            $('#' + targetObjectId).attr('src', output).show();
        },
        failure: function (error) {
            console.log("error: ", error);
            $('#' + targetObjectId)
                .attr('class', 'fas fa-user')
                .attr('style',"color: black").show();
        }
    });
}