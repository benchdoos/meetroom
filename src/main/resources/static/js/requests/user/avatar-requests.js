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
 * Get current user avatar by user id
 *
 * @param context context path of application
 * @param userId user id of user to update image
 * @param targetObjectId id of object to update
 */
function getUserAvatarAndSetToTargetObject(context, userId, targetObjectId) {
    let url = getApiV1Context(context) + "/user-avatar/by-user/" + userId;

    console.log("Requesting user avatar by url:", url);

    updateImage(url, targetObjectId);
}

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
        failure: function (error) {
            console.log("error: ", error);
        }
    });
}