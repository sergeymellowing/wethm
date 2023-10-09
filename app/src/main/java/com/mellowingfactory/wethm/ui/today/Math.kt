package com.mellowingfactory.wethm.ui.today

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.*

fun polygamy(
    sides: Int = 5,
    width: Float,
    percent: List<Int> = listOf(100, 100, 100, 100, 100)
): Path {
    val path = Path()
    if (percent.all { it == 0 }) return path
    val center = width / 2
    val cx = center
    val cy = center

    val x1 = center
    val y1 = center
    val x2 = center
    val y2 = 0

    val time = max(abs(x1 - x2), abs(y1 - y2))

    val points = (0..time.toInt()).map {
        val delta = it.toFloat() / time
        val a = delta * (x2 - x1) + x1
        val b = delta * (y2 - y1) + y1
        Pair(a, b)
    }
    val index = when (percent[0]) {
        0 -> 0
        100 -> points.size - 1
        else -> (points.size / 100.0 * percent[0]).toInt()
    }
    val point = points[index]
    path.moveTo(point.first, point.second)


    for (i in 0..4) {
        val x = i * 2.0 * Math.PI / sides
        val newX = cx + (center - cx) * cos(x) - (0F - cy) * sin(x)
        val newY = cy + (center - cx) * sin(x) + (0F - cy) * cos(x)

        val newPoint = newPoint(center, newX, newY, percent[i])
        path.lineTo(x = newPoint.first, newPoint.second)


    }

    path.lineTo(point.first, point.second)



    return path
}

private fun newPoint(center: Float, x: Double, y: Double, percent: Int): Pair<Float, Float> {
    val x1 = center
    val y1 = center
    val x2 = x
    val y2 = y

    val time = max(abs(x1 - x2), abs(y1 - y2))

    val points = (0..time.toInt()).map {
        val delta = it.toFloat() / time
        val a = delta * (x2 - x1) + x1
        val b = delta * (y2 - y1) + y1
        Pair(a, b)
    }
    val index = when (percent) {
        0 -> 0
        100 -> points.size - 1
        else -> (points.size / 100.0 * percent).toInt()
    }
    val point = points[index]
    return Pair(point.first.toFloat(), point.second.toFloat())
}





fun polygamyPoints(width: Float, cx: Float, cy: Float): List<Pair<Float, Float>> {
    val path = mutableListOf<Pair<Float, Float>>()
    val center = width / 2
//    path.add(Pair(width / 2, 0F))
    for (i in 0..4) {
        val x = i * 72 * Math.PI / 180
        val newX = cx + (center - cx) * cos(x) - (0F - cy) * sin(x)
        val newY = cy + (center - cx) * sin(x) + (0F - cy) * cos(x)
        path.add(Pair(newX.toFloat(), newY.toFloat()))
    }
//    path.add(Pair(width / 2, 0F))

    return path
}


fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}

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
