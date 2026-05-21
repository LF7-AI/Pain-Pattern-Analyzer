package com.steff.painpatternanalyzer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steff.painpatternanalyzer.data.repository.PainRepository
import com.steff.painpatternanalyzer.model.PainEntry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TimelineViewModel(private val repository: PainRepository) : ViewModel() {

    private val _selectedArea = MutableStateFlow<String?>(null)
    val selectedArea: StateFlow<String?> = _selectedArea

    val entries: StateFlow<List<PainEntry>> = _selectedArea
        .flatMapLatest { area ->
            if (area == null) repository.getAllEntries()
            else repository.getEntriesByBodyArea(area)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun filterByArea(area: String?) {
        _selectedArea.value = area
    }

    fun deleteEntry(entry: PainEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }
}