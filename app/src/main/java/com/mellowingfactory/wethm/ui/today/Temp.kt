package com.mellowingfactory.wethm.ui.today

import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.lang.Math.cos
import java.lang.Math.sin

//fun Path.polygon(sides: Int, radius: Float, center: Offset) {
//    val angle = 2.0 * Math.PI / sides
//    moveTo(
//        x = center.x + (radius * cos(0.0)).toFloat(),
//        y = center.y + (radius * sin(0.0)).toFloat()
//    )
//    for (i in 1 until sides) {
//        lineTo(
//            x = center.x + (radius * cos(angle * i)).toFloat(),
//            y = center.y + (radius * sin(angle * i)).toFloat()
//        )
//    }
//    close()
//}

class PolyShape(private val sides: Int, private val width: Float, private val percent: List<Float>) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = polygamy(sides, width, percent))
    }
}

fun Modifier.advancedShadow(
    color: Color = Color(0x333BFFE5),
    alpha: Float = 0.2f,
    cornersRadius: Dp = 2.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 2.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}