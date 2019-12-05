package com.example.kimali.Mission

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PcTimes_DB(

    var pcTimes: String? = ""
)

    {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "pcTimes" to pcTimes
            )

        }
    }
