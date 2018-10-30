package data.mapper

import model.ProfileDataModel
import data.utils.consts.FIREBASE_DEFAULT_VALUE
import data.utils.consts.PROFILE_BIRTH_ID
import data.utils.consts.PROFILE_GENDER_ID
import data.utils.consts.PROFILE_NAME_ID

fun ProfileDataModel.toMap(): Map<String, String> {
    return mapOf(
            PROFILE_NAME_ID to (name ?: FIREBASE_DEFAULT_VALUE),
            PROFILE_BIRTH_ID to (birth ?: FIREBASE_DEFAULT_VALUE),
            PROFILE_GENDER_ID to (gender ?: FIREBASE_DEFAULT_VALUE)
    )
}