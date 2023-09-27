package com.mellowingfactory.wethm.ui.onboardings.user.extentions

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.gray200
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.gray900

@Composable
fun DropDownContainer(
    modifier: Modifier = Modifier,
    isExpandStyle: Boolean = false,
    text: String,
    label: String,
    suffix: String = "",
    onClick: () -> Unit
) = Box(
    modifier = modifier
        .clickable { onClick() }
        .border(
            width = 1.dp,
            color = gray200,
            shape = when(isExpandStyle){
                true -> RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
                false -> RoundedCornerShape(10.dp)
            }
        )
        .height(54.dp)
){

    Text(
        modifier = Modifier
            .padding(start = 16.dp)
            .align(Alignment.CenterStart),
        text = when(text.isEmpty()){
            true -> label
            false -> "$text $suffix"
        },
        style = TextStyle(
            fontSize = 18.sp,
            lineHeight = 42.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            fontWeight = FontWeight(400),
            color = when(text.isEmpty()){
                true -> gray300
                false -> gray900
            },
        )
    )
    Icon(
        modifier = Modifier
            .rotate(when(isExpandStyle) {
                true -> 180f
                false -> 0f
            })
            .padding(horizontal = 16.dp)
            .align(Alignment.CenterEnd),
        imageVector = Icons.Filled.KeyboardArrowDown,
        tint = gray300,
        contentDescription = null
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewDropDownContainer() = WethmTheme{
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        DropDownContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 40.dp),
            text = "String Example",
            label = "Example Label",
            onClick = {}
        )
    }
}