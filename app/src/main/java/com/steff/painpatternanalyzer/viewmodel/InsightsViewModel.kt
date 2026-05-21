package com.steff.painpatternanalyzer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steff.painpatternanalyzer.data.repository.PainRepository
import kotlinx.coroutines.flow.*

data class InsightsState(
    val averagePainLevel: Float = 0f,
    val mostCommonBodyArea: String = "N/A",
    val avgSleepOnHighPain: Float = 0f,
    val avgStressOnHighPain: Float = 0f,
    val hasData: Boolean = false
)

class InsightsViewModel(private val repository: PainRepository) : ViewModel() {

    val state: StateFlow<InsightsState> = repository.getAllEntries()
        .map { entries ->
            if (entries.isEmpty()) return@map InsightsState()

            val highPainEntries = entries.filter { it.painLevel >= 7 }

            InsightsState(
                averagePainLevel = entries.map { it.painLevel }.average().toFloat(),
                mostCommonBodyArea = entries.groupBy { it.bodyArea }
                    .maxByOrNull { it.value.size }?.key ?: "N/A",
                avgSleepOnHighPain = if (highPainEntries.isEmpty()) 0f
                else highPainEntries.map { it.sleepHours }.average().toFloat(),
                avgStressOnHighPain = if (highPainEntries.isEmpty()) 0f
                else highPainEntries.map { it.stressLevel }.average().toFloat(),
                hasData = true
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InsightsState())
}
