package data.utils

import data.firebase.DatabaseManager

fun DatabaseManager.userPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/userdata/$path" } ?: ""
}

fun DatabaseManager.settingsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/userdata/$path" } ?: ""
}

fun DatabaseManager.newsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "news/$path" } ?: ""
}
