package data.utils

import data.firebase.DatabaseManager

fun DatabaseManager.userPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/$path" } ?: ""
}

fun DatabaseManager.settingsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/settings/$path" } ?: ""
}

fun DatabaseManager.newsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "news/$path" } ?: ""
}

fun DatabaseManager.groupsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "groups/$path" } ?: ""
}

fun DatabaseManager.subscriptionsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "subscriptions/$path" } ?: ""
}


fun DatabaseManager.seenPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "seen/${it.uid}/$path" } ?: ""
}

fun DatabaseManager.profilePath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/profile/$path" } ?: ""
}
