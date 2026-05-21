package com.steff.painpatternanalyzer.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PainLevelChart(
    weeklyData: List<Pair<String, Float>>,
    modifier: Modifier = Modifier
) {
    if (weeklyData.isEmpty()) {
        Text(
            text = "No data for this week yet.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    val lineColor = MaterialTheme.colorScheme.primary
    val dotColor = MaterialTheme.colorScheme.tertiary

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val maxPain = 10f
            val stepX = size.width / (weeklyData.size - 1).coerceAtLeast(1)
            val points = weeklyData.mapIndexed { index, (_, painAvg) ->
                Offset(
                    x = index * stepX,
                    y = size.height - (painAvg / maxPain) * size.height
                )
            }

            // Draw line
            if (points.size > 1) {
                val path = Path().apply {
                    moveTo(points.first().x, points.first().y)
                    points.drop(1).forEach { lineTo(it.x, it.y) }
                }
                drawPath(path, color = lineColor, style = Stroke(width = 4f))
            }

            // Draw dots
            points.forEach { point ->
                drawCircle(color = dotColor, radius = 8f, center = point)
            }
        }

        // X-axis labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weeklyData.forEach { (day, _) ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}