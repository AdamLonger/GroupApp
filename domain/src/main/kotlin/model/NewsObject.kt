package model

data class NewsObject(
        val title: String? = null,
        val text: String? = null,
        val image: String? = null
) : FirebaseObject()