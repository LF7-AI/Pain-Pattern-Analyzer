package com.steff.painpatternanalyzer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.steff.painpatternanalyzer.ui.components.bodyAreas
import com.steff.painpatternanalyzer.viewmodel.AddEntryViewModel

val painTypes = listOf("Dull", "Sharp", "Burning", "Throbbing", "Aching", "Stabbing", "Other")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(viewModel: AddEntryViewModel) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Log Pain Entry") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Pain Level
            Text("Pain Level: ${state.painLevel}/10", style = MaterialTheme.typography.titleSmall)
            Slider(
                value = state.painLevel.toFloat(),
                onValueChange = { viewModel.updatePainLevel(it.toInt()) },
                valueRange = 1f..10f,
                steps = 8
            )

            // Stress Level
            Text("Stress Level: ${state.stressLevel}/10", style = MaterialTheme.typography.titleSmall)
            Slider(
                value = state.stressLevel.toFloat(),
                onValueChange = { viewModel.updateStressLevel(it.toInt()) },
                valueRange = 1f..10f,
                steps = 8
            )

            // Sleep Hours
            Text("Sleep Hours: ${"%.1f".format(state.sleepHours)}h", style = MaterialTheme.typography.titleSmall)
            Slider(
                value = state.sleepHours,
                onValueChange = { viewModel.updateSleepHours(it) },
                valueRange = 0f..12f,
                steps = 23
            )

            // Body Area Dropdown
            DropdownField(
                label = "Body Area",
                options = bodyAreas.drop(1), // drop "All"
                selected = state.bodyArea,
                onSelected = { viewModel.updateBodyArea(it) }
            )

            // Pain Type Dropdown
            DropdownField(
                label = "Pain Type",
                options = painTypes,
                selected = state.painType,
                onSelected = { viewModel.updatePainType(it) }
            )

            // Notes
            OutlinedTextField(
                value = state.notes,
                onValueChange = { viewModel.updateNotes(it) },
                label = { Text("Notes (optional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Save Button
            Button(
                onClick = { viewModel.saveEntry() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Entry")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}