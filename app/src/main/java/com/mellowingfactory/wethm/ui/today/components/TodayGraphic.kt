package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.gray20
import com.mellowingfactory.wethm.ui.today.PolyShape
import com.mellowingfactory.wethm.ui.today.polygamy
import com.mellowingfactory.wethm.ui.today.polygamyPoints

@Composable
fun BoxScope.Graphics(
    todayColor: Color,
    todayGraphic: List<Int>,
    weekGraphic: List<Int>,
    boxSize: Int = 250
) {

    for (j in 1..5) {
        var width by remember {
            mutableFloatStateOf(0F)
        }
        var center by remember {
            mutableStateOf(Offset.Zero)
        }
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .width((boxSize - j * 40).dp)
                .height((boxSize - j * 40).dp)
        ) {
            width = size.width
            center = size.center
            val polygram = polygamy(5, width)
            drawPath(
                path = polygram,
                color = gray20,
                style = Stroke(width = 1f),
//                    style = Fill
//                    colorFilter = ColorFilter.tint(gray20),
//                    blendMode = BlendMode.Color
            )
        }


        if (j == 1) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width((boxSize - j * 40).dp)
                    .height((boxSize - j * 40).dp)
                    .graphicsLayer {
                        width = size.width
                        shape = PolyShape(5, width, weekGraphic)
                        clip = true
                    }
                    .background(Color(0x4D10E9EF))
            )


            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width((boxSize - j * 40).dp)
                    .height((boxSize - j * 40).dp)
                    .graphicsLayer {
                        width = size.width
                        shape = PolyShape(5, width, todayGraphic)
                        clip = true
                    }
                    .background(todayColor)

            )
        }


    }
}


fun ContentDrawScope.PolygramText(textMeasurer: TextMeasurer) {
    drawContent()
    //
    val width = size.width
    val p = polygamyPoints(width, center.x, center.y)
    p.forEachIndexed { index, it ->
        when (index) {
            0 -> {
                drawText(
                    textMeasurer,
                    "Deep sleep",
                    topLeft = Offset(it.first - 100, it.second - 60)
                )
            }

            1 -> {
                drawText(
                    textMeasurer,
                    "Efficiency",
                    topLeft = Offset(it.first + 10, it.second - 30)
                )
            }

            2 -> {
                drawText(
                    textMeasurer,
                    "Latency",
                    topLeft = Offset(it.first + 40, it.second - 40)
                )
            }

            3 -> {
                drawText(
                    textMeasurer,
                    "Wake-up\nState",
                    topLeft = Offset(it.first - 200, it.second - 80)
                )
            }

            4 -> {
                drawText(
                    textMeasurer,
                    "Duration",
                    topLeft = Offset(it.first - 150, it.second - 30)
                )
            }
        }


    }
}