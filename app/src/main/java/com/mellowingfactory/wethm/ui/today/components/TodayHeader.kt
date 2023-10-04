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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun TodayHeader(currentPage: Int) {
    val date by remember { mutableStateOf(SimpleDateFormat("yyyy MMM dd").format(Date())) }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = date,
        style = TextStyle(
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
        text = stringResource(id = if (currentPage == 0) R.string.MY_HARMONY else R.string.MY_SLEEP),
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
