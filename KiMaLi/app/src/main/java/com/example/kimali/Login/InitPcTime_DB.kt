package com.example.kimali.Login

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class InitPcTime_DB(

    var pcTime: String? = ""
)

    {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "pcTimes" to pcTime
            )

        }
    }
