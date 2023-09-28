package com.mellowingfactory.wethm.ui.today

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gradient
import com.mellowingfactory.wethm.ui.theme.pageGradient
import kotlinx.coroutines.launch

@Composable
fun TodayScreen() {
    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = pageGradient
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Jan 27, 2023 ",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFFA7AEB9),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "My sleep",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF121212),
                textAlign = TextAlign.Center,
            )
        )


        Box(
            modifier = Modifier
                .padding(top = 56.dp)
                .size(200.dp)
                .background(Color.Black)
        )
        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = "Welcome to wethm",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFFA7AEB9),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "READY",
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_extrabold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF2D3037),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = "--:--   ~  --:--",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF7D8899),
                textAlign = TextAlign.Center,
            )
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Your device is connected!\nWe'll have your first sleep data tomorrow.",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFFA7AEB9),
                textAlign = TextAlign.Center,
            )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 16.dp),
        ) {
            InfoCard()
            InfoCard()
            InfoCard()

        }

    }

    CustomBottomSheet()


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheet() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 120.dp,
        sheetContainerColor = Color.White,
        sheetDragHandle = {
            Card(
                modifier = Modifier
                    .shadow(
                        elevation = 32.dp,
                        spotColor = Color(0xFF91ACC5),
                        ambientColor = Color(0xFF91ACC5)
                    ),
                elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color(0xFFFFFFFF),
                    containerColor = Color(0xFFFFFFFF)
                ),


                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Attributes",

                        // Contents title
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF2D3037),
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null,
                        tint = Color(0xFFA7AEB9)
                    )

                }
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
                    modifier = Modifier
                        .shadow(
                            elevation = 32.dp,
                            spotColor = Color(0xFF91ACC5),
                            ambientColor = Color(0xFF91ACC5)
                        ),
                    border = BorderStroke(1.dp, Color(0xFFECF6FF)),
                    shape = RoundedCornerShape(14.dp),
                    elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = CardDefaults.cardColors(
                        contentColor = Color(0xFFFFFFFF),
                        containerColor = Color(0xFFFFFFFF)
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
                                text = "Cumulative Debt/Surplus",
                                // Contents sub title
                                style = TextStyle(
                                    fontSize = 17.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF6C7584),
                                )
                            )

                            Text(
                                text = "last 7 days",

                                // Contents sub title (info)
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFBCC2CA),
                                    textAlign = TextAlign.Right,
                                )
                            )

                        }

                        Divider(color = Color(0xFFECF6FF), modifier = Modifier.padding(top = 10.dp))

                        Box(
                            modifier = Modifier
                                .padding(top = 22.dp, start = 22.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFF4364C),
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
                        ) {
                            Text(
                                text = "Debt",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFF4364C),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 10.dp, start = 22.dp),
                            text = "2h 18m",
                            style = TextStyle(
                                fontSize = 32.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_extrabold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF3C424A),
                            )
                        )

                        val localDensity = LocalDensity.current
                        var width by remember {
                            mutableStateOf(0.dp)
                        }

                        Box(
                            modifier = Modifier
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
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )

                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxHeight()
                                    .width(1.dp)
                                    .background(Color(0xFF79C0FF))
                            )

                            Icon(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = (width / 100) * 30),
                                painter = painterResource(id = R.drawable.ic_elipce),
                                contentDescription = null,
                                tint = Color(0xFFFFFFFF)
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
                                text = "-",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_light)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFFD70F27),
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
                                    color = Color(0xFF929BA9),
                                    textAlign = TextAlign.Center,
                                )
                            )
                            Text(
                                text = "+",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_light)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF07B5EC),
                                    textAlign = TextAlign.End,
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = "Optimal zone",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF929BA9),
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
                        text = "Vitals and Environment",

                        // Contents sub title
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF6C7584),
                        )
                    )

                    Text(
                        text = "last sleep",

                        // Contents sub title (info)
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFBCC2CA),
                            textAlign = TextAlign.Right,
                        )
                    )

                }
                Spacer(modifier = Modifier.padding(top = 22.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    StatusCard()
                    Spacer(modifier = Modifier.padding(10.dp))
                    StatusCard()
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    StatusCard()
                    Spacer(modifier = Modifier.padding(10.dp))
                    StatusCard()
                }
                Spacer(modifier = Modifier.padding(top = 20.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    StatusCard()
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.padding(40.dp))
            }
        }) {}

}


@Composable
fun RowScope.StatusCard() {
    Row(
        modifier = Modifier
            .weight(1F),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFD0E9FF),
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF53AEFF))
            )
        }


        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {

            Text(
                text = "48-77 Bpm",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF4B515C),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = "Heart Rate",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF7D8899),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@Composable
fun CustomItem(text: String) {
    Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.apple_icon),
            modifier = Modifier.padding(start = 31.dp, top = 9.dp), contentDescription = ""
        )
        Text(
            text = text, modifier = Modifier
                .height(40.dp)
                .padding(start = 20.dp, top = 11.dp),
            fontSize = 18.sp
        )
    }
}


@Composable
private fun InfoCard() {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFD2D6DC),
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
    ) {
        Text(
            modifier = Modifier,//.padding(horizontal = 10.dp, vertical = 4.dp),
            text = "Excellent",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF929BA9),
                textAlign = TextAlign.Center,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            )
        )
    }

}
