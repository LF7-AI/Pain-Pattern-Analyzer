package com.steff.painpatternanalyzer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pain_entries")
data class PainEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val painLevel: Int,           // 1–10
    val bodyArea: String,         // e.g. "Back", "Head", "Knee"
    val painType: String,         // e.g. "Sharp", "Dull", "Burning"
    val stressLevel: Int,         // 1–10
    val sleepHours: Float,        // e.g. 6.5
    val notes: String,            // free text, can be empty
    val timestamp: Long           // System.currentTimeMillis()
)
