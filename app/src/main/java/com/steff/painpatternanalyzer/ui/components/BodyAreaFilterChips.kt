package com.steff.painpatternanalyzer.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val bodyAreas = listOf("All", "Back", "Head", "Neck", "Knee", "Shoulder", "Chest", "Abdomen", "Hip", "Other")

@Composable
fun BodyAreaFilterChips(
    selectedArea: String?,
    onAreaSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState())
    ) {
        bodyAreas.forEach { area ->
            val isSelected = if (area == "All") selectedArea == null else selectedArea == area

            FilterChip(
                selected = isSelected,
                onClick = {
                    onAreaSelected(if (area == "All") null else area)
                },
                label = { Text(area) }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}