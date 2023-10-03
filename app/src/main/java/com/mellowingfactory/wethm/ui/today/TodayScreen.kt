package com.mellowingfactory.wethm.ui.today

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray1000
import com.mellowingfactory.wethm.ui.theme.gray20
import com.mellowingfactory.wethm.ui.theme.gray320
import com.mellowingfactory.wethm.ui.theme.gray500
import com.mellowingfactory.wethm.ui.theme.pageGradient
import com.mellowingfactory.wethm.ui.today.components.Graphics
import com.mellowingfactory.wethm.ui.today.components.InfoCard
import com.mellowingfactory.wethm.ui.today.components.PolygramText
import com.mellowingfactory.wethm.ui.today.components.TodayBottomSheet
import com.mellowingfactory.wethm.ui.today.components.TodayHeader
import com.mellowingfactory.wethm.ui.today.components.TodayToolbar


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodayScreen() {

    val vm = remember { TodayViewModel() }
    val state by vm.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = pageGradient
                )
            )
    ) {

        TodayToolbar()

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {
                val pagerState = rememberPagerState(1) {
                    2
                }
                TodayHeader(pagerState.currentPage)

                HorizontalPager(state = pagerState) {
                    Box {
                        when (it) {
                            0 -> {
                                MyHarmonyContainer()
                            }

                            1 -> {
                                MySleepContainer(state)

                            }
                        }
                    }
                }

            }

            TodayBottomSheet(state)
        }


    }

}


@Composable
private fun MyHarmonyContainer() {
    Box(modifier = Modifier.fillMaxSize())
}


@Composable
private fun MySleepContainer(state: TodayState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var points by remember {
            mutableStateOf(listOf<Pair<Float, Float>>())
        }
        val textMeasurer = rememberTextMeasurer()

        Box(modifier = Modifier
            .padding(top = 40.dp)
            .width(400.dp)
            .onGloballyPositioned {
                points = polygamyPoints(
                    it.size.width.toFloat(),
                    it.size.center.x.toFloat(),
                    it.size.center.y.toFloat()
                )
            }
            .drawWithContent {
                PolygramText(textMeasurer)
            }) {
            Graphics()
        }
        val status = state.status

        Text(
            modifier = Modifier.padding(top = 40.dp), text = status.title, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = gray320,
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 8.dp), text = status.status, style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                fontWeight = FontWeight(700),
                color = gray1000,
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = status.time, style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = gray500,
                textAlign = TextAlign.Center,
            )
        )

        if (status !is TodayStatus.Active) {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = status.description,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(400),
                    color = gray320,
                    textAlign = TextAlign.Center,
                )
            )
        }


        if (status is TodayStatus.Active) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 16.dp),
            ) {
                status.info.forEach {
                    InfoCard(it)
                }

            }
        }


    }
}



