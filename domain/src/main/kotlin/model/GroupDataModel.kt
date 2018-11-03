package model

data class GroupDataModel(
        val name: String? = null,
        val description: String? = null,
        val latestDate: String? = null,
        val image: String? = null
) : FirebaseObject()