/**
 * Creates url with api v1 prefix
 * @param context application context
 * @returns {string} context/api/v1
 */
function getApiV1Context(context) {
    return context + "api/v1";
}

/**
 * Update user username
 *
 * @param context application context root url
 * @param targetErrorObjectId id of target error block
 * @param changeUsernameDto dto with new username
 */
function updateUserUsername(context, targetErrorObjectId, changeUsernameDto) {
    let url = getApiV1Context(context) + "/user/update-username";

    $('#' + targetErrorObjectId).html("").hide();

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
            location.reload();
        },
        error: function (XMLHttpRequest, error) {
            console.log("error: ", error);
            $('#' + targetErrorObjectId).html(XMLHttpRequest.responseJSON.message).show();
        }
    });
}

/**
 * Update user avatar preview image
 *
 * @param context context path of application
 * @param userId user id of user to update image
 * @param targetObjectId id of object to update
 */
function updateUserAvatarPreview(context, userId, targetObjectId) {
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
            $('#' + targetObjectId).attr('src', output.src).show();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("error: ", XMLHttpRequest.responseJSON);
            $('#' + targetObjectId)
                .attr('class', 'fas fa-user')
                .attr('style', "color: black").show();
        }
    });
}

/**
 * Update user information
 *
 * @param context root url
 * @param userId id of user to update
 * @param targetErrorObjectId target object to display errors
 * @param userUpdateInfoDto dto with new info
 */
function updateUserInfo(context, userId, formId, targetErrorObjectId, userUpdateInfoDto) {
    let url = getApiV1Context(context) + "/user/update/" + userId;
    console.log(url, userId, userUpdateInfoDto);

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(userUpdateInfoDto),
        contentType: "application/json",
        success: function (output, status, xhr) {
            location.reload();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            let responseJSON = XMLHttpRequest.responseJSON;
            $('#' + targetErrorObjectId).html(responseJSON.message).show();

            appendValidationErrors(responseJSON.errors, formId);
        }
    });
}

/**
 * Update user password
 *
 * @param context root url
 * @param targetErrorObjectId target object to display errors
 * @param userId id of user
 * @param updateUserPasswordDto dto with new password info
 */
function updateUserPassword(context, targetErrorObjectId, userId, updateUserPasswordDto) {
    let url = getApiV1Context(context) + "/user/update-password/" + userId;
    console.log(url, userId);

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(updateUserPasswordDto),
        contentType: "application/json",
        success: function (output, status, xhr) {
            location.reload();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#' + targetErrorObjectId).html(XMLHttpRequest.responseJSON.message).show();
        }
    });
}

/**
 * Update user email address
 *
 * @param context root url
 * @param targetErrorObjectId for erros
 * @param userId id of user
 * @param newUserEmail new user email
 */
function updateUserEmail(context, formId, targetErrorObjectId, userId, newUserEmail) {
    let url = getApiV1Context(context) + "/user/update-email/" + userId;
    console.log(url, userId);

    let updateUserEmailDto = new UpdateUserEmailDto(newUserEmail);

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(updateUserEmailDto),
        contentType: "application/json",
        success: function (output, status, xhr) {
            location.reload();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            let responseJSON = XMLHttpRequest.responseJSON;
            $('#' + targetErrorObjectId).html(responseJSON.message).show();

            let errors = responseJSON.errors;
            appendValidationErrors(errors, formId);
        }
    });
}