package com.mellowingfactory.wethm.ui.today.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray1100
import com.mellowingfactory.wethm.ui.theme.gray150
import com.mellowingfactory.wethm.ui.theme.gray320

@Composable
fun TodayHeader(currentPage: Int) {
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
