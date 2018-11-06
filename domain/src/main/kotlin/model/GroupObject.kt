package model

import org.threeten.bp.LocalDateTime

data class GroupObject(
        val name: String? = null,
        val description: String? = null,
        val latestDate: LocalDateTime? = null,
        val image: String? = null,
        override var objectKey: String? = null,
        val isNotSeen: Boolean = false
) : FirebaseObject()
