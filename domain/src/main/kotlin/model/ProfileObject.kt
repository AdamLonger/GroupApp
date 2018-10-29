package model

data class ProfileObject(
        val name: String? = null,
        val birth: String? = null,
        val sex: String? = null
) : FirebaseObject()