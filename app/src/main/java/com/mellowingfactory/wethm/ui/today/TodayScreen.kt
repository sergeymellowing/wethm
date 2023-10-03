package com.mellowingfactory.wethm.ui.today

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue200
import com.mellowingfactory.wethm.ui.theme.gr
import com.mellowingfactory.wethm.ui.theme.gradient
import com.mellowingfactory.wethm.ui.theme.gray1000
import com.mellowingfactory.wethm.ui.theme.gray1100
import com.mellowingfactory.wethm.ui.theme.gray15
import com.mellowingfactory.wethm.ui.theme.gray150
import com.mellowingfactory.wethm.ui.theme.gray20
import com.mellowingfactory.wethm.ui.theme.gray320
import com.mellowingfactory.wethm.ui.theme.gray400
import com.mellowingfactory.wethm.ui.theme.gray500
import com.mellowingfactory.wethm.ui.theme.gray600
import com.mellowingfactory.wethm.ui.theme.gray800
import com.mellowingfactory.wethm.ui.theme.gray900
import com.mellowingfactory.wethm.ui.theme.green400
import com.mellowingfactory.wethm.ui.theme.pageGradient
import com.mellowingfactory.wethm.ui.theme.red600
import com.mellowingfactory.wethm.ui.theme.white


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

        Toolbar()

        Box(modifier = Modifier.fillMaxSize()) {

            Column(modifier = Modifier.fillMaxSize()) {
                val pagerState = rememberPagerState(1) {
                    2
                }
                Header(pagerState.currentPage)

                HorizontalPager(state = pagerState) {
                    Box {
                        when (it) {
                            0 -> {
                                MyHarmony()
                            }

                            1 -> {
                                Content(state)

                            }
                        }
                    }
                }

            }

            CustomBottomSheet(state)
        }


    }

}

@Composable
private fun Header(currentPage: Int) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Jan 27, 2023 ", style = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(500),
            color = gray320,
            textAlign = TextAlign.Center,
        )
    )

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        text = if (currentPage == 0) "My harmony" else "My sleep",
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
            fontWeight = FontWeight(600),
            color = gray1100,
            textAlign = TextAlign.Center,
        )
    )



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val harmony =
            if (currentPage == 0) gray1100 else gray150
        val sleep =
            if (currentPage == 1) gray1100 else gray150
        Box(
            modifier = Modifier
                .height(3.dp)
                .width(20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(harmony)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        Box(
            modifier = Modifier
                .height(3.dp)
                .width(20.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(sleep)
        )

    }
}


@Composable
private fun Toolbar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Blue)
    ) {
        Text(text = "TempToolbar")
    }
}


@Composable
private fun MyHarmony() {
    Box(modifier = Modifier.fillMaxSize())
}


@Composable
private fun Content(state: TodayState) {
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

@Composable
private fun BoxScope.Graphics(boxSize: Int = 250) {
    val textMeasurer = rememberTextMeasurer()

    for (j in 1..5) {
        Canvas(
            modifier = Modifier

                .align(Alignment.Center)
                .width((boxSize - j * 40).dp)
                .height((boxSize - j * 40).dp)

        ) {
            val width = size.width
            val polygram = polygamy(width, center.x, center.y)
            drawPath(
                path = polygram,
                color = gray20,
                style = Stroke(width = 1f),
//                    style = Fill
//                    colorFilter = ColorFilter.tint(gray20),
//                    blendMode = BlendMode.Color
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet(state: TodayState) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 64.dp,
        sheetContainerColor = white,
        sheetShadowElevation = 24.dp,
        sheetShape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        sheetDragHandle = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.TODAY_ATTRIBUTES),
                    // Contents title
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                        fontWeight = FontWeight(600),
                        color = gray1000,
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    tint = gray320
                )

            }
        },
        sheetContent = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 16.dp)
            ) {
                Card(
                    modifier = Modifier.shadow(
                        elevation = 32.dp,
                        spotColor = gr,
                        ambientColor = gr
                    ),
                    border = BorderStroke(1.dp, gray15),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = white, containerColor = white
                    ),


                    ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(id = R.string.TODAY_DEBT_SUR),
                                // Contents sub title
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                                    fontWeight = FontWeight(600),
                                    color = gray600,
                                )
                            )

                            Text(
                                text = stringResource(id = R.string.TODAY_LAST7),

                                // Contents sub title (info)
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(400),
                                    color = gray150,
                                    textAlign = TextAlign.Right,
                                )
                            )

                        }

                        Divider(color = gray15, modifier = Modifier.padding(top = 10.dp))

                        Box(
                            modifier = Modifier
                                .padding(top = 22.dp, start = 22.dp)
                                .border(
                                    width = 1.dp,
                                    color = state.currentStateColor,
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = state.currentState, style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                    fontWeight = FontWeight(500),
                                    color = state.currentStateColor,
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 22.dp),
                            text = state.hour,
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                                fontWeight = FontWeight(700),
                                color = gray900,
                            )
                        )

                        val localDensity = LocalDensity.current
                        var width by remember {
                            mutableStateOf(0.dp)
                        }

                        Box(modifier = Modifier
                            .padding(top = 22.dp, start = 22.dp, end = 22.dp)
                            .onGloballyPositioned { coordinates ->
                                width =
                                    with(localDensity) { (coordinates.size.width).toDp() - 16.dp }
                            }
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = gradient,
                                ), shape = RoundedCornerShape(12.dp)
                            )

                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(blue200)
                            )

                            Icon(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = (width / 100) * 30),
                                painter = painterResource(id = R.drawable.ic_elipce),
                                contentDescription = null,
                                tint = white
                            )


                        }

                        Row(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "-", style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_light)),
                                    fontWeight = FontWeight(400),
                                    color = red600,
                                    textAlign = TextAlign.Start,
                                )
                            )
                            Text(
                                modifier = Modifier.padding(start = 2.dp),
                                text = "0",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(400),
                                    color = gray400,
                                    textAlign = TextAlign.Center,
                                )
                            )
                            Text(
                                text = "+", style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_light)),
                                    fontWeight = FontWeight(400),
                                    color = green400,
                                    textAlign = TextAlign.End,
                                )
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = stringResource(id = R.string.OPTIMAL_ZONE),
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(400),
                                    color = gray400,
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }


                }

                Spacer(modifier = Modifier.padding(top = 32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.TODAY_VIT_N_ENV),

                        // Contents sub title
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                            fontWeight = FontWeight(600),
                            color = gray600,
                        )
                    )

                    Text(
                        text = stringResource(id = R.string.TODAY_LAST_SLEEP),

                        // Contents sub title (info)
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(400),
                            color = gray150,
                            textAlign = TextAlign.Right,
                        )
                    )

                }
                val vitals = state.vitals.chunked(2)
                vitals.forEach {
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        it.forEachIndexed { index, vitalAndEnvironment ->
                            StatusCard(vitalAndEnvironment)
                            if (index == 0) {
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                        }

                    }
                }


                Spacer(modifier = Modifier.padding(100.dp))
            }
        }) {}

}


@Composable
fun RowScope.StatusCard(vitalAndEnvironment: VitalAndEnvironment) {
    Row(
        modifier = Modifier.weight(1F), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .border(
                    width = 1.dp,
                    color = vitalAndEnvironment.borderColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = vitalAndEnvironment.iconId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(vitalAndEnvironment.iconColor)
            )
        }


        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {

            Text(
                text = vitalAndEnvironment.valueStr, style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    fontWeight = FontWeight(600),
                    color = gray800,
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = vitalAndEnvironment.type, style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight(500),
                    color = gray500,
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}


@Composable
private fun InfoCard(text: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp, color = gray20, shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
    ) {
        Text(
            modifier = Modifier,//.padding(horizontal = 10.dp, vertical = 4.dp),
            text = text, style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(500),
                color = gray400,
                textAlign = TextAlign.Center,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.Both
                )
            )
        )
    }

}
