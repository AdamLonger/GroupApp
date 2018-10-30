package com.wanari.meetingtimer.presentation.model

import com.wanari.meetingtimer.common.model.GenderEnum
import com.wanari.meetingtimer.common.utils.toFirebaseString
import com.wanari.meetingtimer.common.utils.toLocalDate
import model.ProfileDataModel
import org.threeten.bp.LocalDate
import util.Optional

data class ProfileObject(
        val name: String? = null,
        val birth: LocalDate? = null,
        val gender: GenderEnum? = null
)

fun ProfileObject.parse(dataModel: Optional<ProfileDataModel>): ProfileObject {
    return ProfileObject(
            dataModel.component1()?.name,
            dataModel.component1()?.birth?.toLocalDate(),
            dataModel.component1()?.gender?.run { GenderEnum.valueOf(this) }
    )
}

fun ProfileObject.toDataModel(): ProfileDataModel {
    return ProfileDataModel(
            name,
            birth?.toFirebaseString(),
            gender?.name
    )
}
