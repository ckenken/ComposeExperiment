package com.ckenken.composeexperiment.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FullLineChart(
    dataPoints: List<Pair<String, Float>>, // e.g. ("5/1", 63.5f)
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF4CAF50),
    ySteps: Int = 5
) {
    Canvas(modifier = modifier) {
        if (dataPoints.isEmpty()) return@Canvas

        val paddingStart = 90f
        val paddingBottom = 40f
        val dates = dataPoints.map { it.first }
        val values = dataPoints.map { it.second }

        val maxY = values.maxOrNull() ?: 0f
        val minY = values.minOrNull() ?: 0f
        val chartWidth = size.width - paddingStart
        val chartHeight = size.height - paddingBottom

        val yInterval = (maxY - minY) / ySteps
        val xGap = chartWidth / (values.size - 1).coerceAtLeast(1)
        val yRatio = if (maxY != minY) chartHeight / (maxY - minY) else 1f

        val points = values.mapIndexed { index, value ->
            val x = index * xGap + paddingStart
            val y = chartHeight - (value - minY) * yRatio
            Offset(x, y)
        }

        // Draw Y axis labels and horizontal grid lines
        for (i in 0..ySteps) {
            val yValue = minY + i * yInterval
            val y = chartHeight - (yValue - minY) * yRatio

            // Grid line
            drawLine(
                color = Color.LightGray,
                start = Offset(paddingStart, y),
                end = Offset(size.width, y),
                strokeWidth = 1.dp.toPx()
            )

            val textSize = 28f
            val yLabelOffsetY = textSize * 0.3f // 補償 baseline 偏移

            drawContext.canvas.nativeCanvas.drawText(
                "%.1f".format(yValue),
                24f,
                y + yLabelOffsetY, // 往下補一點點
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    this.textSize = textSize
                    textAlign = android.graphics.Paint.Align.LEFT
                }
            )
        }

        // Draw X axis labels
        points.forEachIndexed { i, point ->
            drawContext.canvas.nativeCanvas.drawText(
                dates[i],
                point.x,
                chartHeight + 30f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Draw line path
        val path = Path().apply {
            points.forEachIndexed { index, point ->
                if (index == 0) moveTo(point.x, point.y)
                else lineTo(point.x, point.y)
            }
        }
        drawPath(path, color = lineColor, style = Stroke(width = 4.dp.toPx()))

        // Draw data point circles
        points.forEach {
            drawCircle(color = lineColor, radius = 6.dp.toPx(), center = it)
        }
    }
}


@Preview
@Composable
fun WeightTrendPage() {
    val weightData = listOf(
        "4/30" to 65f,
        "5/1" to 63.5f,
        "5/2" to 64.2f,
        "5/3" to 66f,
        "5/4" to 64.5f,
        "5/5" to 62f
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("體重趨勢", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        FullLineChart(
            dataPoints = weightData,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}