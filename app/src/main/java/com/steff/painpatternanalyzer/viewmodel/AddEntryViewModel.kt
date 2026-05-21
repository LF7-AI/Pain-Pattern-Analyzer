package com.steff.painpatternanalyzer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steff.painpatternanalyzer.data.repository.PainRepository
import com.steff.painpatternanalyzer.model.PainEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AddEntryState(
    val painLevel: Int = 5,
    val bodyArea: String = "Back",
    val painType: String = "Dull",
    val stressLevel: Int = 5,
    val sleepHours: Float = 7f,
    val notes: String = ""
)

class AddEntryViewModel(private val repository: PainRepository) : ViewModel() {

    private val _state = MutableStateFlow(AddEntryState())
    val state: StateFlow<AddEntryState> = _state

    fun updatePainLevel(value: Int) { _state.value = _state.value.copy(painLevel = value) }
    fun updateBodyArea(value: String) { _state.value = _state.value.copy(bodyArea = value) }
    fun updatePainType(value: String) { _state.value = _state.value.copy(painType = value) }
    fun updateStressLevel(value: Int) { _state.value = _state.value.copy(stressLevel = value) }
    fun updateSleepHours(value: Float) { _state.value = _state.value.copy(sleepHours = value) }
    fun updateNotes(value: String) { _state.value = _state.value.copy(notes = value) }

    fun saveEntry() {
        val s = _state.value
        viewModelScope.launch {
            repository.insertEntry(
                PainEntry(
                    painLevel = s.painLevel,
                    bodyArea = s.bodyArea,
                    painType = s.painType,
                    stressLevel = s.stressLevel,
                    sleepHours = s.sleepHours,
                    notes = s.notes,
                    timestamp = System.currentTimeMillis()
                )
            )
            _state.value = AddEntryState() // reset form after save
        }
    }
}