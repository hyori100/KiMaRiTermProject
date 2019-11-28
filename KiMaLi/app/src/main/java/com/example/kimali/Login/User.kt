package com.example.kimali.Login

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var user_pw: String? = "",
    var who: String? = ""
)