package com.steff.painpatternanalyzer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steff.painpatternanalyzer.data.repository.PainRepository
import com.steff.painpatternanalyzer.model.PainEntry
import kotlinx.coroutines.flow.*

data class DashboardState(
    val totalEntries: Int = 0,
    val averagePainLevel: Float = 0f,
    val recentEntries: List<PainEntry> = emptyList(),
    val weeklyData: List<Pair<String, Float>> = emptyList()
)

class DashboardViewModel(private val repository: PainRepository) : ViewModel() {

    val state: StateFlow<DashboardState> = repository.getAllEntries()
        .map { entries -> buildDashboardState(entries) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardState())

    private fun buildDashboardState(entries: List<PainEntry>): DashboardState {
        if (entries.isEmpty()) return DashboardState()

        val average = entries.map { it.painLevel }.average().toFloat()
        val recent = entries.take(5)
        val weekly = buildWeeklyData(entries)

        return DashboardState(
            totalEntries = entries.size,
            averagePainLevel = average,
            recentEntries = recent,
            weeklyData = weekly
        )
    }

    private fun buildWeeklyData(entries: List<PainEntry>): List<Pair<String, Float>> {
        val sevenDaysAgo = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L
        val formatter = java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault())

        return entries
            .filter { it.timestamp >= sevenDaysAgo }
            .groupBy { formatter.format(java.util.Date(it.timestamp)) }
            .map { (day, dayEntries) -> day to dayEntries.map { it.painLevel }.average().toFloat() }
    }
}
