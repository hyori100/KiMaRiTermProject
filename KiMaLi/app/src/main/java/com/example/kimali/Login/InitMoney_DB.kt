package com.example.kimali.Login

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class InitMoney_DB(
    var moneys: String? = "moneys"
)