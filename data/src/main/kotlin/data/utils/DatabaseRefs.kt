package data.utils

import data.firebase.DatabaseManager

const val NO_AUTH_PATH = ""

fun DatabaseManager.settingsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/settings/$path" }
        ?: NO_AUTH_PATH
}

fun DatabaseManager.newsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "news/$path" } ?: NO_AUTH_PATH
}

fun DatabaseManager.groupsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "groups/$path" } ?: NO_AUTH_PATH
}

fun DatabaseManager.subscriptionsPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "subscriptions/${it.uid}/$path" }
        ?: NO_AUTH_PATH
}


fun DatabaseManager.seenPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "seen/${it.uid}/$path" } ?: NO_AUTH_PATH
}

fun DatabaseManager.profilePath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "users/${it.uid}/profile/$path" }
        ?: NO_AUTH_PATH
}

fun DatabaseManager.messagingTokensPath(path: String = ""): String {
    return authManager.getCurrentUserBlocking()?.let { "messagingTokens/${it.uid}/$path" }
        ?: NO_AUTH_PATH
}
