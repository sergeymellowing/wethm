package com.mellowingfactory.wethm.ui.today

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray1000
import com.mellowingfactory.wethm.ui.theme.gray320
import com.mellowingfactory.wethm.ui.theme.gray500
import com.mellowingfactory.wethm.ui.theme.pageGradient
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.ui.today.components.Graphics
import com.mellowingfactory.wethm.ui.today.components.InfoCard
import com.mellowingfactory.wethm.ui.today.components.PolygonText
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
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        TodayToolbar()
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                val pagerState = rememberPagerState(1) { 2 }
                TodayHeader(pagerState.currentPage)

                HorizontalPager(state = pagerState) {
                    Box {
                        when (it) {
                            0 -> MyHarmonyContainer()
                            1 -> MySleepContainer(state)
                        }
                    }
                }
            }

            if (state.status is TodayStatus.TooShort || state.status is TodayStatus.Active) {
                TodayBottomSheet(state)
            }

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



        val textMeasurer = rememberTextMeasurer()
        val status = state.status
        val context = LocalContext.current
        val gData = state.gData.map {
            val image = ImageVector.vectorResource(id = it.second)
            val painter = rememberVectorPainter(image = image)
            Pair(context.getString(it.first), painter)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp)
            .width(250.dp)
            .height(250.dp)
            .drawWithContent {
                if (size.height > 0){
                    drawContent()
                    PolygonText(textMeasurer, gData,state.isEmpty)
                }

            }

        ) {

            Graphics(state.todayGraphicColor, state.todayGraphic, state.weekGraphic)

            if (status !is TodayStatus.Ready) {
                RadarInfo(state)
            }
        }


        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = status.title),
            style = TextStyle(
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
                text = stringResource(id = status.description),
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


@Composable
private fun BoxScope.RadarInfo(state: TodayState) {
    val status = state.status
    ConstraintLayout(modifier = Modifier.align(Alignment.Center)) {
        val (number, percent, diff) = createRefs()
        Text(
            modifier = Modifier.constrainAs(number) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },

            text = state.todayAvgStr,
            style = TextStyle(
                fontSize = 50.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = white,
                textAlign = TextAlign.Center,
            )
        )
        Text(
            modifier = Modifier.constrainAs(percent) {
                start.linkTo(number.end)
                bottom.linkTo(number.bottom, 12.dp)
            },
            text = "%",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(800),
                color = white,
            )
        )

        if (status !is TodayStatus.None && status !is TodayStatus.TooShort && status !is TodayStatus.Analyzing) {
            Text(
                modifier = Modifier.constrainAs(diff) {
                    top.linkTo(number.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                text = "${state.weekAvgDif} %",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    fontWeight = FontWeight(400),
                    color = white,
                    textAlign = TextAlign.Center,
                )
            )
        }


    }
}

