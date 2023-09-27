package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray100
import com.mellowingfactory.wethm.ui.theme.white


private val topBarHeight = 44.dp
@Composable
fun AuthLayout(
    title: String = "",
    headerContent: @Composable BoxScope.() -> Unit,
    roundedContent: Dp = 22.dp,
    isExpand: Boolean = true,
    hasBack: Boolean = true,
    hasSkip: Boolean = false,
    showBranding: Boolean = true,
    onSkipClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) = Box(
    modifier = Modifier
        .fillMaxSize()
){
    val heightHeader = remember { mutableStateOf(0.dp) }
    BackgroundGradient(
        showBranding = showBranding
    ) {
        heightHeader.value = max(
            0.dp,
            it - roundedContent
        )
    }
    TitleBar(
        title = title,
        isExpand = isExpand,
        hasBack = hasBack,
        hasSkip = hasSkip,
        onSkipClick = onSkipClick,
        onBackClick = onBackClick
    )
    HeaderContainer(
        height = when(isExpand){
            true -> heightHeader.value
            false -> 0.dp
                               },
        headerContent = headerContent
    )
    ContentContainer(
        paddingTop = when(isExpand){
            true -> heightHeader.value
            false -> topBarHeight + topBarHeight
        },
        roundedContent = roundedContent,
        content = content
    )
}

@Composable
private fun HeaderContainer(
    height: Dp,
    headerContent: @Composable BoxScope.() -> Unit
) = Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(height),
    content = headerContent
)

@Composable
private fun ContentContainer(
    paddingTop: Dp,
    roundedContent: Dp,
    content: @Composable BoxScope.() -> Unit
) = Box(
    modifier = Modifier
        .padding(top = paddingTop)
        .fillMaxSize()
        .background(
            color = white,
            shape = RoundedCornerShape(
                topStart = roundedContent,
                topEnd = roundedContent,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ),
    content = content
)

@Composable
private fun TitleBar(
    title: String,
    isExpand: Boolean,
    hasBack: Boolean,
    hasSkip: Boolean,
    onSkipClick: () -> Unit,
    onBackClick: () -> Unit
) = Box(
    modifier = Modifier
        .statusBarsPadding()
        .height(topBarHeight)
        .fillMaxWidth()
){
    if (!isExpand){
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = title,
            style = TextStyle(
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(600),
                color = white,
                textAlign = TextAlign.Center,
            )
        )
    }
    if (hasBack) {
        IconButton(
            modifier = Modifier.padding(8.dp),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                tint = white,
                contentDescription = null
            )
        }
    }
    if (hasSkip){
        Text(
            modifier = Modifier
                .clickable { onSkipClick() }
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd),
            text = "skip",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(500),
                color = gray100,
            )
        )
    }
}

@Composable
private fun BackgroundGradient(
    showBranding: Boolean,
    onHeightChanged: (Dp) -> Unit
) = Box{
    val localDensity = LocalDensity.current
    Image(
        modifier = Modifier
            .onSizeChanged {
                onHeightChanged(
                    with(localDensity) {
                        it.height.toDp()
                    }
                )
            }
            .fillMaxWidth(),
        painter = painterResource(R.drawable.gradient_auth),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
    if (showBranding) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .alpha(0.1f)
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.branding_icon),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun PreviewAuthLayout() = WethmTheme{
    val isExpand = remember {
        mutableStateOf(true)
    }
    AuthLayout(
        title = "Test Title",
        hasSkip = true,
        isExpand = isExpand.value,
        headerContent = {
//            Text(
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(start = 32.dp),
//                text = stringResource(R.string.SIGNUP_PASSWORD_SET),
//                style = TextStyle(
//                    fontSize = 18.sp,
//                    lineHeight = 23.sp,
//                    fontFamily = FontFamily(Font(R.font.pretendard_light)),
//                    fontWeight = FontWeight(300),
//                    color = white,
//                )
//            )
        }
    ){
        Text(
            modifier = Modifier
                .clickable {
                    isExpand.value = !isExpand.value
                },
            text = "This is content\n".repeat(20)
        )
    }
}