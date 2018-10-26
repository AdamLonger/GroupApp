package model

data class NewsObject(
        val name: String? = null,
        val description: String? = null
) : FirebaseObject()