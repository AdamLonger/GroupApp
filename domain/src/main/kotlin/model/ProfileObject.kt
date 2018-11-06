package model

import enums.GenderEnum
import org.threeten.bp.LocalDate

data class ProfileObject(
        val name: String? = null,
        val birth: LocalDate? = null,
        val gender: GenderEnum? = null
)
