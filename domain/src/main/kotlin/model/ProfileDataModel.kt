package model

data class ProfileDataModel(
        var name: String? = null,
        var birth: String? = null,
        var gender: String? = null
) : FirebaseObject()
