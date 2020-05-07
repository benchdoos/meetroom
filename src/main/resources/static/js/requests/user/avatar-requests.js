/**
 * Generate random avatar and change target object
 *
 * @param context context path of application
 * @param targetObjectId id of object to update
 */
function generateNewAvatar(context, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/random";

    console.log("URL:", context, url);
    updateImage(url, targetObjectId);
}

/**
 * Get current user avatar by given key
 *
 * @param context context path of application
 * @param key to generate custom avatar
 * @param targetObjectId id of object to update
 */
function generateAvatarByGivenName(context, key, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/by-key/" + key;

    console.log("URL:", context, url);
    updateImage(url, targetObjectId);
}

/**
 * Update src attribute of image by src of gravatar avatar by given email
 *
 * @param {string} context context
 * @param {string} email of user
 * @param {string} targetObjectId
 */
function updateImageSrcByGravatarEmail(context, email, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/gravatar/" + email;
    console.log("Getting gravatar image by url: ", url);

    updateImage(url, targetObjectId);
}

/**
 * Send GET ajax request for api, by given url. Replaces `src` attribute for given `targetObjectId`.
 *
 * @param url to send request
 * @param targetObjectId to update in case of success
 */
function updateImage(url, targetObjectId) {
    $.ajax({
        type: 'GET',
        url: url,
        contentType: "application/json",
        success: function (output, status, xhr) {
            $('#' + targetObjectId).attr('src', output.src).show();
        },
        error: function (error) {
            console.log("error: ", error);
        }
    });
}

/**
 * Update user avatar
 *
 * @param context application context root url
 * @param targetObjectId id of model to hide
 * @param targetErrorObjectId id of object where to show errors
 * @param userAvatarImageToReplaceId user image
 * @param userId id of user
 * @param updateUserAvatarDto dto with avatar data
 */
function updateUserAvatar(context, targetObjectId, targetErrorObjectId, userAvatarImageToReplaceId, userId, updateUserAvatarDto) {
    let url = getApiV1Context(context) + "/user-avatar/update/" + userId;
    console.log("Getting gravatar image by url: ", url);

    $('#' + targetErrorObjectId).html("").hide();

    $.ajax({
        type: 'POST',
        url: url,
        data: JSON.stringify(updateUserAvatarDto),
        contentType: "application/json",
        success: function (output, status, xhr) {
            $('#' + targetObjectId).modal('hide');
            $('#' + userAvatarImageToReplaceId).attr('src', output.src);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#' + targetErrorObjectId).html(XMLHttpRequest.responseJSON.message).show();
        }
    });
}