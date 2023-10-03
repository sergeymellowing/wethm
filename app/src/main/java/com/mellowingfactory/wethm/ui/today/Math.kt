package com.mellowingfactory.wethm.ui.today

import androidx.compose.ui.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

fun polygamy(width: Float, cx: Float, cy: Float, percent: Float = 0F): Path {
    val path = Path()
    val center = width / 2
    var cx = center
    var cy = center
    if (percent != 0F) {
        cx = (center + cx) / 2
        cy = (0 + cy) / 2
//        path.moveTo(cx, cy)
    } else {
        path.moveTo(center, 0F)
    }

    for (i in 0..4) {
        val x = i * 72 * Math.PI / 180
        val newX = cx + (center - cx) * cos(x) - (0F - cy) * sin(x)
        val newY = cy + (center - cx) * sin(x) + (0F - cy) * cos(x)
//        xc = (xa + xb) / 2;
//        yc = (ya + yb) / 2
//        if (percent != 0F) {
//            val xc = (center + newX.toFloat()) / percent
//            val yc = (center + newY.toFloat()) / percent
//            path.lineTo(x = xc, y = yc)
//        } else {
        path.lineTo(x = newX.toFloat(), y = newY.toFloat())
//        }


    }

//    if (percent != 0F) {
//        val x = 0 * 72 * Math.PI / 180
//        val newX =
//            cx + (center - cx) * cos(x) - (0F - cy) * sin(x)
//        val newY =
//            cy + (center - cx) * sin(x) + (0F - cy) * cos(x)
//
//        val xc = (center + newX) / percent
//        val yc = (center + newY) / percent
//        path.lineTo(xc.toFloat(), yc.toFloat())
//    } else {
    path.lineTo(center, 0F)
//    }

    return path
}

fun List<Pair<Float, Float>>.toPath(width: Float): Path {
    val path = Path()
    val center = width / 2
    path.moveTo(center, 0F)
    this.forEach {
        path.lineTo(x = it.first, y = it.second)
    }
    path.lineTo(center, 0F)
    return path
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
