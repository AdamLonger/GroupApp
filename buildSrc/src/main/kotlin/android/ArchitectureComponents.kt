package android

import Versions.androidXPaging

object ArchitectureComponents {
    const val runtime = "android.arch.lifecycle:runtime:${Versions.androidArchitecture}"
    const val extensions = "android.arch.lifecycle:extensions:${Versions.androidArchitecture}"
    const val compiler = "android.arch.lifecycle:compiler:${Versions.androidArchitecture}"
    const val paging = "android.arch.paging:runtime:$androidXPaging"
}
