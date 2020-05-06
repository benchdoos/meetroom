/**
 * Dto to update user username
 *
 * @param {string} oldUsername old username
 * @param {string} newUsername new username
 */
function UpdateUserUsernameDto(oldUsername, newUsername) {
    return {
        oldUsername: oldUsername,
        newUsername: newUsername
    };
}

/**
 * Dto to update  user avatar
 *
 * @param type of avatar
 * @param data of avatar
 * @returns {{data: string, type: string}}
 * @constructor
 */
function UpdateUserAvatarDto(type, data) {
    return {
        type: type,
        data: data
    };
}

/**
 * Update user information dto
 *
 * @param firstName of user
 * @param lastName of user
 * @constructor
 */
function UpdateUserInfoDto(firstName, lastName) {
    return {
        firstName: firstName,
        lastName: lastName
    }
}