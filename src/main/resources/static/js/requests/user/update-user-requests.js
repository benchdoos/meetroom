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
    console.log("updateUserUsername", url);

    $('#' + targetErrorObjectId).html("").hide();

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
    console.log("updateUserInfo", url);

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
    console.log("updateUserPassword", url);

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
 * @param modalId to show errors (current modal usually)
 * @param targetErrorObjectId for errors
 * @param successDialog to show on success
 * @param userId id of user
 * @param newUserEmail new user email
 */
function updateUserEmail(context, modalId, targetErrorObjectId, successDialog, userId, newUserEmail) {
    let url = getApiV1Context(context) + "/user/update-email/" + userId;
    console.log("updateUserEmail", url);

    let updateUserEmailDto = new UpdateUserEmailDto(newUserEmail);

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(updateUserEmailDto),
        contentType: "application/json",
        success: function (output, status, xhr) {
            $('#' + modalId).modal('hide');
            showInfoDialog(successDialog, "Email update", "Email requests were successfully sent");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            let responseJSON = XMLHttpRequest.responseJSON;
            $('#' + targetErrorObjectId).html(responseJSON.message).show();

            let errors = responseJSON.errors;
            appendValidationErrors(errors, formId);
        }
    });
}

/**
 *
 * @param context
 * @param modalId
 * @param targetErrorObjectId
 * @param userId
 */
function sendUserActivationLink(context,  modalId, targetErrorObjectId, userId) {
    let url = getApiV1Context(context) + '/manage/user/send-activation-request/' + userId;
    console.log("sendUserActivationLink", url);

    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        success: function (output, status, xhr) {
            $('#' + modalId).modal('hide');
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            let responseJSON = XMLHttpRequest.responseJSON;
            $('#' + targetErrorObjectId).html(responseJSON.message).show();
        }
    });
}

/**
 * Update modal dialog title and message
 *
 * @param successDialog dialog id
 * @param title text to set on title
 * @param message text to set on message
 */
function showInfoDialog(successDialog, title, message) {
    let modal = $('#' + successDialog).modal();
    modal.find('.modal-title').text(title);
    modal.find('.modal-body').text(message);
    modal.modal('show');
}
