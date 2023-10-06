package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue200
import com.mellowingfactory.wethm.ui.theme.gr
import com.mellowingfactory.wethm.ui.theme.gradient
import com.mellowingfactory.wethm.ui.theme.gray1000
import com.mellowingfactory.wethm.ui.theme.gray15
import com.mellowingfactory.wethm.ui.theme.gray150
import com.mellowingfactory.wethm.ui.theme.gray320
import com.mellowingfactory.wethm.ui.theme.gray400
import com.mellowingfactory.wethm.ui.theme.gray600
import com.mellowingfactory.wethm.ui.theme.gray900
import com.mellowingfactory.wethm.ui.theme.green400
import com.mellowingfactory.wethm.ui.theme.red600
import com.mellowingfactory.wethm.ui.theme.white
import com.mellowingfactory.wethm.ui.today.TodayState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayBottomSheet(state: TodayState) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 48.dp,
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
                                    color = state.currentStatusColor,
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = stringResource(id = state.currentStatus), style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                    fontWeight = FontWeight(500),
                                    color = state.currentStatusColor,
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
                        var height by remember {
                            mutableStateOf(0.dp)
                        }

                        Box(modifier = Modifier
                            .padding(top = 22.dp, start = 22.dp, end = 22.dp)
                            .onGloballyPositioned { coordinates ->
                                width =
                                    with(localDensity) { (coordinates.size.width).toDp() - 16.dp }
                                height =
                                    with(localDensity) { (coordinates.size.height).toDp() - 16.dp }
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
                                    .align(Alignment.Center)
                                    .fillMaxHeight()
                                    .padding(start = (width / 200) * 30),
                                painter = painterResource(id = R.drawable.ic_punktir),
                                contentDescription = null,
                                tint = white
                            )

                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxHeight()
                                    .padding(end = (width / 200) * 30),
                                painter = painterResource(id = R.drawable.ic_punktir),
                                contentDescription = null,
                                tint = white
                            )

                            if (state.currentStatusValue > 0) {
                                val value = (width / 200) * state.currentStatusValue
                                val alignment =
                                    if (value < width) Alignment.Center else Alignment.CenterEnd
                                val padding = if (alignment == Alignment.Center) value else 0.dp
                                Icon(
                                    modifier = Modifier
                                        .align(alignment)
                                        .fillMaxHeight()
                                        .padding(start = padding),
                                    painter = painterResource(id = R.drawable.ic_elipce),
                                    contentDescription = null,
                                    tint = white
                                )
                            } else {
                                val value = (width / 200) * state.currentStatusValue * -1
                                val alignment =
                                    if (value < width) Alignment.Center else Alignment.CenterStart
                                val padding = if (alignment == Alignment.Center) value else 0.dp
                                Icon(
                                    modifier = Modifier
                                        .align(alignment)
                                        .fillMaxHeight()
                                        .padding(end = padding),
                                    painter = painterResource(id = R.drawable.ic_elipce),
                                    contentDescription = null,
                                    tint = white
                                )
                            }


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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        it.forEachIndexed { index, vitalAndEnvironment ->
                            StatusCard(vitalAndEnvironment)
                        }

                    }
                }


                Spacer(modifier = Modifier.padding(100.dp))
            }
        }) {}

}