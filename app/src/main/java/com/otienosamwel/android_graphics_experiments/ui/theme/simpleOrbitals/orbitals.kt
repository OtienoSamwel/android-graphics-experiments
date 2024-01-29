package com.otienosamwel.android_graphics_experiments.ui.theme.simpleOrbitals

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.otienosamwel.android_graphics_experiments.ui.theme.AndroidgraphicsexperimentsTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Orbitals() {
    val infiniteTransition = rememberInfiniteTransition(label = "animate sizes")

    var pathPoints by remember { mutableStateOf(listOf(Pair(0f, 0f))) }

    val orbitalPathOne by remember { mutableStateOf(Path()) }

    val animatedSize = infiniteTransition.animateFloat(
        initialValue = 1F,
        targetValue = 10F,
        animationSpec = InfiniteRepeatableSpec(
            tween(
                durationMillis = 1000,
                easing = LinearEasing,
                delayMillis = 0
            ), repeatMode = RepeatMode.Reverse
        ), label = "animate size"
    )

    val animatedSizeSun = infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 60F,
        animationSpec = InfiniteRepeatableSpec(
            tween(
                durationMillis = 10000,
                easing = LinearEasing,
                delayMillis = 0
            ), repeatMode = RepeatMode.Reverse
        ), label = "animate size"
    )

    val animateSunColor by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = Color.Red,
        animationSpec = InfiniteRepeatableSpec(
            tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    var offSetX by remember { mutableStateOf(0.dp) }
    var offSetY by remember { mutableStateOf(0.dp) }

    var offSetX1 by remember { mutableStateOf(0.dp) }
    var offSetY1 by remember { mutableStateOf(0.dp) }


    var time by remember { mutableFloatStateOf(0F) }

    LaunchedEffect(Unit) {
        while (true) {
            time += 0.01F
            val next = computeNextPoint(radius = 100F, time = time)
            val next1 = computeNextPoint(radius = 200F, time = time + 4)
            offSetX = next.x.dp
            offSetY = next.y.dp
            offSetX1 = next1.x.dp
            offSetY1 = next.y.dp

            //path for orbital once
            pathPoints =
                addAndRemove(list = pathPoints, newValue = Pair(offSetX1.value, offSetY1.value))

            //trace the path
            pathPoints.forEachIndexed { index: Int, item: Pair<Float, Float> ->
                if (index == 0) orbitalPathOne.moveTo(
                    item.first,
                    item.second
                ) else orbitalPathOne.lineTo(item.first, item.second)
            }
            delay(10)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {

        drawCircle(
            Color.Red,
            radius = 30f,
            center = Offset(size.width / 2, size.height / 2)
        )
        drawCircle(
            color = Color.Yellow,
            radius = 20f,
            center = Offset(
                size.width / 2 + offSetX.toPx(),
                size.height / 2 + offSetY.toPx()
            )
        )

        drawCircle(
            color = Color.White,
            radius = 10f,
            center = Offset(
                size.width / 2 + offSetX1.toPx(),
                size.height / 2 + offSetY1.toPx()
            )
        )



        translate(left = size.width / 2, top = size.height / 2) {
            drawPath(path = orbitalPathOne, color = Color.Blue)
            //drawCircle(color = Color.White)
        }
    }
}


@Preview
@Composable
fun OrbitalsPreview() {
    AndroidgraphicsexperimentsTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Orbitals()
        }
    }
}

/**
 * A bouncy orbital that changes color and bounciness depending on it's position on the screen
 *
 */


fun computeNextPoint(
    radius: Float,
    angularVelocity: Float = 1F,
    time: Float
): Offset {
    val newXOffset = (radius * cos(angularVelocity * time))
    val newYOffset = (radius * sin(angularVelocity * time))
    return Offset(newXOffset, newYOffset)
}


fun addAndRemove(
    list: List<Pair<Float, Float>>,
    newValue: Pair<Float, Float>
): List<Pair<Float, Float>> {
    val finalList = list.toMutableList().also {
        if (it.size > 30) {
            it.removeAt(0)
        }
        it.add(newValue)
    }
    return finalList.toList()
}