package com.example.kimali.compensation

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ModifyMoney_DB(
    var moneys: String? = "moneys"
)

{

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "moneys" to moneys
        )

    }
}