package com.mellowingfactory.wethm.ui.today

import android.os.Build.VERSION_CODES.Q
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

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

class PolyShape(private val sides: Int, private val width: Float, private val percent: List<Int>) :
    Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = polygamy(sides, width, percent))
    }
}


@Immutable
data class Shadow(
    @Stable val offsetX: Dp,
    @Stable val offsetY: Dp,
    @Stable val radius: Dp,
    @Stable val color: Color,
)

fun Modifier.withShadow(
    shadow: Shadow = Shadow(
        radius = 5.dp,
//        color = Color(0xFF673AB7),
        color = Color.Black,
        offsetX = 80.dp,
        offsetY = 80.dp
    ),
    shape: Shape,
) = drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        paint.asFrameworkPaint().apply {
            this.color = Color.Transparent.toArgb()
            setShadowLayer(
                shadow.radius.toPx(),
                shadow.offsetX.toPx(),
                shadow.offsetY.toPx(),
                shadow.color.toArgb()
            )
        }
        val outline = shape.createOutline(size, layoutDirection, this)
        canvas.drawOutline(outline, paint)
    }
}

@RequiresApi(Q)
fun Modifier.advancedShadow(
    color: Color = Color(0x330066FF),
    alpha: Float = 0.5f,
    cornersRadius: Dp = 10.dp,
    shadowBlurRadius: Dp = 10.dp,
    offsetY: Dp = 34.dp,
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