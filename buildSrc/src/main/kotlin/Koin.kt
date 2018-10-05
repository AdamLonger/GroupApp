
object Koin {
    // Koin for Android
    private const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    // or Koin for Lifecycle scoping
    private const val koinAndroidScope = "org.koin:koin-android-scope:${Versions.koin}"
    // or Koin for Android Architecture ViewModel
    private const val koinAndroidViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    val all = listOf(koinAndroid, koinAndroidScope, koinAndroidViewModel)
}
