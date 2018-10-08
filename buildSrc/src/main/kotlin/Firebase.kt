object Firebase {
    private const val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    private const val database = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"
    private const val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    private const val storage = "com.google.firebase:firebase-storage:${Versions.firebaseStorage}"

    private const val rxDatabase = "com.androidhuman.rxfirebase2:firebase-database:16.0.1.1"
    private const val rxAuthKotlin = "com.androidhuman.rxfirebase2:firebase-auth-kotlin:16.0.2.0"
    private const val rxAuth = "com.androidhuman.rxfirebase2:firebase-auth:16.0.2.0"

    val all = listOf(core, database, auth, storage, rxDatabase, rxAuth, rxAuthKotlin)
}
