package com.example.kimali.Mission

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Moneys(

    var moneys: String? = ""
)

    {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "moneys" to moneys
            )

        }
    }
