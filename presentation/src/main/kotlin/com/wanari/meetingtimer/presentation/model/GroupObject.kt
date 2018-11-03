package com.wanari.meetingtimer.presentation.model

import com.wanari.meetingtimer.common.utils.toLocalDateTime
import model.GroupDataModel
import org.threeten.bp.LocalDateTime
import util.Optional

data class GroupObject(
        val name: String? = null,
        val description: String? = null,
        val latestDate: LocalDateTime? = null,
        val image: String? = null,
        val key: String? = null
)

fun GroupObject.parse(dataModel: Optional<GroupDataModel>): GroupObject {
    return GroupObject(
            dataModel.component1()?.name,
            dataModel.component1()?.description,
            dataModel.component1()?.latestDate?.toLocalDateTime(),
            dataModel.component1()?.image,
            dataModel.component1()?.objectKey
    )
}
