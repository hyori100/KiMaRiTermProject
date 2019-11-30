package com.example.kimali.Parent_first_view

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChildTopic(

    var topic: String? = ""
)

    {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "topic" to topic
            )

        }
    }
