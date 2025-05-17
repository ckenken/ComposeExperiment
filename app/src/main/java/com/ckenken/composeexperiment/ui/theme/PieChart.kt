package com.ckenken.composeexperiment.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    values: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
) {
    val sum = values.sum()
    val angles = values.map { 360 * it / sum }

    Canvas(modifier = modifier) {
        var startAngle = -90f
        for (i in values.indices) {
            drawArc(
                color = colors[i],
                startAngle = startAngle,
                sweepAngle = angles[i],
                useCenter = true
            )
            startAngle += angles[i]
        }
    }
}

@Preview
@Composable
fun Preview() {
    val values = listOf(70f, 20f, 10f) // 代表良好、過高、過低
    val colors = listOf(Color.Gray, Color.LightGray, Color.DarkGray)

    PieChart(
        values = values,
        colors = colors,
        modifier = Modifier
            .size(150.dp)
            .padding(16.dp)
    )

}
