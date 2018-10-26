package data.utils

import data.firebase.DatabaseManager

fun DatabaseManager.userPath(path: String = ""): String {
    return firebaseAuth.currentUser?.let { "users/${it.uid}/userdata/$path" } ?: ""
}

fun DatabaseManager.settingsPath(path: String = ""): String {
    return firebaseAuth.currentUser?.let { "users/${it.uid}/userdata/$path" } ?: ""
}

fun DatabaseManager.newsPath(path: String = ""): String {
    return firebaseAuth.currentUser?.let { "news/$path" } ?: ""
}
