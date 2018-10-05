object Firebase {
    private const val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    private const val database = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"
    private const val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuth}"
    private const val storage = "com.google.firebase:firebase-storage:${Versions.firebaseStorage}"

    val all = listOf(core, database, auth, storage)
}
