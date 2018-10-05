object Rx {
    private const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    private const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    private const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    private const val rxshare = "com.jakewharton.rx2:replaying-share-kotlin:${Versions.rxshare}"
    private const val rxpermissions = "com.vanniktech:rxpermission:${Versions.rxpermissions}"

    val all = arrayOf(rxjava, rxandroid, rxkotlin, rxshare)
}
