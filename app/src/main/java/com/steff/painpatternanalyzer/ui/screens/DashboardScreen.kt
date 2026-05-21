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
import com.steff.painpatternanalyzer.ui.components.PainEntryCard
import com.steff.painpatternanalyzer.ui.components.PainLevelChart
import com.steff.painpatternanalyzer.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Dashboard") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Avg Pain",
                    value = "${"%.1f".format(state.averagePainLevel)}/10",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Total Entries",
                    value = "${state.totalEntries}",
                    modifier = Modifier.weight(1f)
                )
            }

            // Weekly Chart
            Text("This Week", style = MaterialTheme.typography.titleMedium)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                PainLevelChart(
                    weeklyData = state.weeklyData,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Recent Entries
            if (state.recentEntries.isNotEmpty()) {
                Text("Recent Entries", style = MaterialTheme.typography.titleMedium)
                state.recentEntries.forEach { entry ->
                    PainEntryCard(entry = entry, onDelete = {})
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}