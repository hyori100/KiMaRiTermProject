package com.example.kimali.Mission

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OneMission_DB (
    var mission_message:String?="",
    var money:String?="",
    var pcTime:String?="",
    var deadLineString:String?=""
)

{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "mission_message" to mission_message,
            "money" to money,
            "pcTime" to pcTime,
            "deadLineString" to deadLineString
        )

    }
}