package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray20
import com.mellowingfactory.wethm.ui.theme.gray400
import com.mellowingfactory.wethm.ui.theme.gray500
import com.mellowingfactory.wethm.ui.theme.gray800
import com.mellowingfactory.wethm.ui.today.VitalAndEnvironment

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
fun InfoCard(text: String) {
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