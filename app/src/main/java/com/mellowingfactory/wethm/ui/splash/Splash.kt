package com.mellowingfactory.wethm.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.splashMellowingFactoryTextStyle

@Composable
fun Splash() = Column(
    modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .padding(bottom = 21.dp),
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally
){
    BrandingContent()
    MellowingfactoryText()
}

@Composable
private fun BrandingContent() = Box(
    modifier = Modifier.fillMaxWidth(),
    contentAlignment = Alignment.BottomCenter
) {
    Image(
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        painter = painterResource(id = R.drawable.splash_background_image),
        contentDescription = null
    )
    Image(
        modifier = Modifier
            .width(150.dp)
            .padding(bottom = 21.dp),
        contentScale = ContentScale.FillWidth,
        painter = painterResource(id = R.drawable.splash_branding),
        contentDescription = null
    )
}

@Composable
private fun MellowingfactoryText(){
    Text(
        modifier = Modifier,
        text = stringResource(R.string.mellowingfactory),
        style = splashMellowingFactoryTextStyle
    )
}