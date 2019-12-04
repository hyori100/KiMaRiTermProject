package com.example.kimali.Mission

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OneMission (
    var mission_message:String?="",
    var money:Int=0,
    var pcTime:Int=0,
    var deadLineString:String?="",
    var dday:Int=0
)