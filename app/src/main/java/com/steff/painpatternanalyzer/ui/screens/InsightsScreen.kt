package com.steff.painpatternanalyzer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.steff.painpatternanalyzer.viewmodel.InsightsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightsScreen(viewModel: InsightsViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Insights") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!state.hasData) {
                Text(
                    text = "Log some entries to see insights.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                InsightCard(
                    title = "Average Pain Level",
                    value = "${"%.1f".format(state.averagePainLevel)} / 10"
                )
                InsightCard(
                    title = "Most Affected Area",
                    value = state.mostCommonBodyArea
                )
                InsightCard(
                    title = "Avg Sleep on High Pain Days",
                    value = "${"%.1f".format(state.avgSleepOnHighPain)} hours"
                )
                InsightCard(
                    title = "Avg Stress on High Pain Days",
                    value = "${"%.1f".format(state.avgStressOnHighPain)} / 10"
                )
            }
        }
    }
}

@Composable
fun InsightCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}