package com.example.kimali.Login

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Child(
    var user_pw: String? = "",

    var nameText: String? = "",
    var topic: String = ""
)