package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.defaultButtonGradient
import com.mellowingfactory.wethm.ui.theme.white

@Composable
fun ButtonGradient(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    text: String,
    onClick: () -> Unit,
    colors: List<Color> = defaultButtonGradient
) {
    Box(
        modifier = modifier
            .alpha(when(enable){
                true -> 1f
                false -> 0.3f
            })
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset.Zero,
                    end = Offset.Infinite
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { if (enable) onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            color = white
        )
    }
}

@Preview
@Composable
private fun PreviewButtonGradient(){
    ButtonGradient(
        text = "Click Me",
        enable = true,
        onClick = {  }
    )
}