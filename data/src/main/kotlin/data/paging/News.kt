package data.paging

import data.firebase.FirebaseObject

data class News(
        val name: String? = null,
        val description: String? = null
) : FirebaseObject()