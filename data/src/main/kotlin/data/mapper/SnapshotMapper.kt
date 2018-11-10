package data.mapper

import com.google.firebase.database.DataSnapshot
import com.longer.groupapp.common.utils.*
import model.ProfileDataModel

fun DataSnapshot.getProfile(): ProfileDataModel {
    (this.value as? HashMap<*, *>)?.let { map ->
        return ProfileDataModel(
                map[PROFILE_NAME_ID] as? String,
                map[PROFILE_BIRTH_ID] as? String,
                map[PROFILE_GENDER_ID] as? String
        )
    }
    return ProfileDataModel()
}
