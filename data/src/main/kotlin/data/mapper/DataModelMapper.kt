package data.mapper

import com.wanari.meetingtimer.common.utils.nullIfEmpty
import com.wanari.meetingtimer.common.utils.toFirebaseString
import com.wanari.meetingtimer.common.utils.toLocalDate
import com.wanari.meetingtimer.common.utils.toLocalDateTime
import data.utils.consts.FIREBASE_DEFAULT_VALUE
import data.utils.consts.PROFILE_BIRTH_ID
import data.utils.consts.PROFILE_GENDER_ID
import data.utils.consts.PROFILE_NAME_ID
import enums.GenderEnum
import model.GroupDataModel
import model.GroupObject
import model.ProfileDataModel
import model.ProfileObject

fun ProfileDataModel.toMap(): Map<String, String> {
    return mapOf(
            PROFILE_NAME_ID to (name ?: FIREBASE_DEFAULT_VALUE),
            PROFILE_BIRTH_ID to (birth ?: FIREBASE_DEFAULT_VALUE),
            PROFILE_GENDER_ID to (gender ?: FIREBASE_DEFAULT_VALUE)
    )
}

fun ProfileDataModel.toObject(): ProfileObject {
    return ProfileObject(
            name,
            birth?.nullIfEmpty()?.toLocalDate(),
            gender?.nullIfEmpty()?.run { GenderEnum.valueOf(this) }
    )
}

fun ProfileObject.toDataModel(): ProfileDataModel {
    return ProfileDataModel(
            name,
            birth?.toFirebaseString(),
            gender?.name
    )
}

fun GroupDataModel.toObject(): GroupObject {
    return GroupObject(
            name,
            description,
            latestdate?.toLocalDateTime(),
            image,
            objectKey
    )
}
