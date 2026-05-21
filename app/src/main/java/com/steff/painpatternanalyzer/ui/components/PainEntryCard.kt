package com.steff.painpatternanalyzer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.steff.painpatternanalyzer.model.PainEntry
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PainEntryCard(
    entry: PainEntry,
    onDelete: (PainEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.bodyArea,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Pain: ${entry.painLevel}/10",
                    style = MaterialTheme.typography.titleMedium,
                    color = painLevelColor(entry.painLevel)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${entry.painType}  •  ${timeFormatter.format(Date(entry.timestamp))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (entry.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = entry.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sleep: ${entry.sleepHours}h  •  Stress: ${entry.stressLevel}/10",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(onClick = { onDelete(entry) }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun painLevelColor(level: Int) = when {
    level <= 3 -> MaterialTheme.colorScheme.primary
    level <= 6 -> MaterialTheme.colorScheme.tertiary
    else       -> MaterialTheme.colorScheme.error
}