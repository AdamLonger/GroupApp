package data.paging

import model.FirebaseObject

data class News(
        val name: String? = null,
        val description: String? = null
) : FirebaseObject()