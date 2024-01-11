package com.otienosamwel.android_graphics_experiments.ui.theme.doodles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.otienosamwel.android_graphics_experiments.ui.theme.AndroidgraphicsexperimentsTheme


@Composable
fun AnalogClock() {
    val colors = listOf(Color.White, Color.Blue)
    Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
        drawCircle(
            brush = Brush.linearGradient(colors),
            style = Stroke(width = 10f)
        )

        drawArc(
            brush = Brush.linearGradient(colors),
            startAngle = 0f,
            sweepAngle = 1f,
            useCenter = true,
        )
    })
}


@Preview
@Composable
fun AnalogClockPreview() {
    AndroidgraphicsexperimentsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AnalogClock()
        }
    }
}