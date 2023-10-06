package com.mellowingfactory.wethm.ui.today

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

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

class PolyShape(private val sides: Int, private val width: Float, private val percent: List<Int>) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = polygamy(sides, width, percent))
    }
}