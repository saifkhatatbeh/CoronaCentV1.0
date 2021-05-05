package com.v1.Tammeni.data

import com.google.firebase.database.Exclude

data class User(
    val name: String = "", val username: String = "", val email: String = "",
    val photo: String? = null, @Exclude val uid: String = ""
)