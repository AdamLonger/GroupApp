package data.mapper

import com.google.firebase.database.DataSnapshot
import com.wanari.meetingtimer.common.utils.PROFILE_BIRTH_ID
import com.wanari.meetingtimer.common.utils.PROFILE_GENDER_ID
import com.wanari.meetingtimer.common.utils.PROFILE_NAME_ID
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
