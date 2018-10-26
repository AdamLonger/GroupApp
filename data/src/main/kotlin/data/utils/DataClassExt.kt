package data.utils

import model.SettingsObject

fun SettingsObject.toMap(): Map<String, *> {
    return mapOf(
            SETTINGS_EXAMPLE_VALUE to exampleData
    )
}