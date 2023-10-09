package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray20
import com.mellowingfactory.wethm.ui.today.PolyShape
import com.mellowingfactory.wethm.ui.today.polygamy
import com.mellowingfactory.wethm.ui.today.polygamyPoints

@Composable
fun BoxScope.Graphics(
    todayColor: List<Color>,
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
            )
        }


        if (j == 1) {
            if (width > 0) {
                if (!weekGraphic.all { it == 0 }) {
                    val weekShape = PolyShape(5, width, weekGraphic)
                    val weekColor by remember { mutableStateOf(Color(0x333BFFE6).copy(alpha = 0.2F)) }
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
                            .background(weekColor)
                            .border(1.dp, weekColor.copy(alpha = 1F), weekShape)
                    )

                }

                if (!todayGraphic.all { it == 0 }) {

                    val todayShape = PolyShape(5, width, todayGraphic)
                    val sloganBrush = Brush.linearGradient(
                        colors = todayColor,
                        start = Offset(75F, 32F),
                        end = Offset(143F, 168F),
                    )


                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .width((boxSize - j * 40).dp)
                            .height((boxSize - j * 40).dp)
                            .graphicsLayer {
                                width = size.width
                                shape = todayShape
                                clip = true
                            }
                            .background(sloganBrush, todayShape, alpha = 0.5F)
                            .border(1.dp, todayColor.last(), todayShape)

                    )
                }


            }

        }


    }
}

private fun DrawScope.drawTextWithStyle(
    textMeasurer: TextMeasurer,
    text: String,
    topLeft: Offset = Offset.Zero,
) {
    drawText(
        textMeasurer,
        text = text,
        topLeft = topLeft,
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(400),
            color = Color(0xFF929BA9),
            textAlign = TextAlign.End
        ),
    )
}


fun ContentDrawScope.PolygonText(
    textMeasurer: TextMeasurer,
    data: List<Pair<String, VectorPainter>>,
    isEmpty: Boolean
) {
    try {
        val width = size.width
        val p = polygamyPoints(width, center.x, center.y)

        p.forEachIndexed { index, it ->
            val painter = data[index].second
            val text = data[index].first
            val logoHeight = painter.intrinsicSize.height
            val logoWidth = painter.intrinsicSize.width
            when (index) {
                0 -> {
                    if (!isEmpty) {
                        translate(
                            left = it.first - logoWidth / 2,
                            top = it.second - logoHeight
                        ) {
                            with(painter) {
                                draw(painter.intrinsicSize)
                            }
                        }
                    }

                    val textX = if (isEmpty) {
                        val size = textMeasurer.measure(text)
                        it.first - size.size.width/2
                    } else
                        it.first + painter.intrinsicSize.width + 4 - logoWidth / 2

                    val textY = if (isEmpty) {
                        it.second -  textMeasurer.measure(text).size.height / 4
                    } else it.second - logoHeight + textMeasurer.measure(text).size.height / 4
                    drawTextWithStyle(
                        textMeasurer = textMeasurer,
                        text = text,
                        topLeft = Offset(
                            x = textX,
                            y = textY
                        ),
                    )
                }

                1 -> {
                    if (!isEmpty) {
                        translate(
                            left = it.first + logoWidth / 2,
                            top = it.second - logoHeight
                        ) {
                            with(painter) {
                                draw(painter.intrinsicSize)
                            }
                        }
                    }
                    val textX = if (isEmpty) {
                        it.first - textMeasurer.measure(text).size.width / 5
                    } else
                        it.first + painter.intrinsicSize.width + 4 + logoWidth / 2


                    val textY = if (isEmpty) {
                        it.second
                    } else
                        it.second - logoHeight + textMeasurer.measure(text).size.height / 4


                    drawTextWithStyle(
                        textMeasurer = textMeasurer,
                        text = text,
                        topLeft = Offset(
                            x = textX,
                            y = textY
                        ),
                    )
                }

                2 -> {
                    if (!isEmpty) {
                        translate(
                            left = it.first + logoWidth,
                            top = it.second - logoHeight
                        ) {
                            with(painter) {
                                draw(painter.intrinsicSize)
                            }
                        }
                    }


                    val textX = if (isEmpty) {
                        it.first
                    } else
                        it.first + painter.intrinsicSize.width + 4 + logoWidth


                    val textY = if (isEmpty) {
                        it.second - textMeasurer.measure(text).size.height * 1.5F
                    } else
                        it.second - logoHeight + textMeasurer.measure(text).size.height / 4


                    drawTextWithStyle(
                        textMeasurer = textMeasurer,
                        text = text,
                        topLeft = Offset(
                            x = textX,
                            y = textY
                        ),
                    )
                }

                3 -> {
                    if (!isEmpty) {
                        translate(
                            left = it.first - 2 * logoWidth,
                            top = it.second - logoHeight
                        ) {
                            with(painter) {
                                draw(painter.intrinsicSize)
                            }
                        }
                    }

                    val textX = if (isEmpty) {
                        it.first - textMeasurer.measure(text).size.width
                    } else
                        it.first - textMeasurer.measure(text).size.width - 2 * logoWidth


                    val textY = if (isEmpty) {
                        it.second - textMeasurer.measure(text).size.height
                    } else
                        it.second - logoHeight - textMeasurer.measure(text).size.height / 4


                    drawTextWithStyle(
                        textMeasurer = textMeasurer,
                        text = text,
                        topLeft = Offset(
                            x = textX,
                            y = textY
                        ),
                    )
                }

                4 -> {
                    if (!isEmpty) {
                        translate(
                            left = it.first - logoWidth,
                            top = it.second - logoHeight
                        ) {
                            with(painter) {
                                draw(painter.intrinsicSize)
                            }
                        }
                    }

                    val textX = if (isEmpty) {
                        it.first - textMeasurer.measure(text).size.width/2  - textMeasurer.measure(text).size.width/4
                    } else
                        it.first - textMeasurer.measure(text).size.width - logoWidth / 2


                    val textY = if (isEmpty) {
                        it.second
                    } else
                        it.second - logoHeight + textMeasurer.measure(text).size.height / 4



                    drawTextWithStyle(
                        textMeasurer = textMeasurer,
                        text = text,
                        topLeft = Offset(
                            x = textX,
                            y = textY
                        ),
                    )
                }
            }


        }
    } catch (e: Exception) {

    }


}

