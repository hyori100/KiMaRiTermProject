package com.example.kimali.Mission

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OneMission (
    var mission_message:String?="",
    var money:String?="",
    var pcTime:String?="",
    var deadLineString:String?="",
    var dday:Int=0
)