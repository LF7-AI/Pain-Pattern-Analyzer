package com.steff.painpatternanalyzer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.steff.painpatternanalyzer.model.PainEntry
import com.steff.painpatternanalyzer.ui.components.BodyAreaFilterChips
import com.steff.painpatternanalyzer.ui.components.PainEntryCard
import com.steff.painpatternanalyzer.viewmodel.TimelineViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(viewModel: TimelineViewModel) {
    val entries by viewModel.entries.collectAsState()
    val selectedArea by viewModel.selectedArea.collectAsState()

    val grouped = entries.groupBy { entry ->
        SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
            .format(Date(entry.timestamp))
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Timeline") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            BodyAreaFilterChips(
                selectedArea = selectedArea,
                onAreaSelected = { viewModel.filterByArea(it) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "No entries yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    grouped.forEach { (date, dayEntries) ->
                        item {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                        items(dayEntries, key = { it.id }) { entry ->
                            PainEntryCard(
                                entry = entry,
                                onDelete = { viewModel.deleteEntry(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}