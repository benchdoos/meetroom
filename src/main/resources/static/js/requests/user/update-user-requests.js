
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

/**
 * Update user avatar preview image
 *
 * @param context context path of application
 * @param userId user id of user to update image
 * @param targetObjectId id of object to update
 */
function updateUserAvatar(context, userId, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/by-user/" + userId;

    updateUserAvatarImage(url, targetObjectId);
}

/**
 * Send GET ajax request for api, by given url. Replaces `src` attribute for given `targetObjectId`.
 * In case of failure it will make set the default icon to `targetObjectId`
 *
 * @param url to send request
 * @param targetObjectId target object id to change src attribute value
 */
function updateUserAvatarImage(url, targetObjectId) {
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
                .attr('style', "color: black").show();
        }
    });
}
