package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DashedDivider(
    thickness: Dp = 1.dp,
    color: Color = Color(0xFFD0E9FF),
    phase: Float = 3f,
    intervals: FloatArray = floatArrayOf(5f, 5f),
    modifier: Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val dividerHeight = thickness.toPx()
        rotate(0F) {
            drawRoundRect(
                color = color,
                style = Stroke(
                    width = dividerHeight,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = intervals,
                        phase = phase
                    )
                )
            )
        }
    }
}