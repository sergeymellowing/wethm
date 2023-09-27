package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue10
import com.mellowingfactory.wethm.ui.theme.blue200
import com.mellowingfactory.wethm.ui.theme.blue400
import com.mellowingfactory.wethm.ui.theme.gray900
import com.mellowingfactory.wethm.ui.theme.red400

@Composable
fun CodeField(
    modifier: Modifier = Modifier,
    length: Int,
    isError: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    Main(length, value, isError)
    TransparentTextField(value, length, onValueChange)
}

@Composable
private fun TransparentTextField(
    value: String,
    length: Int,
    onValueChange: (String) -> Unit
) = TextField(
    value = value,
    onValueChange = {
        if (it.length <= length) {
            onValueChange(it)
        }
    },
    modifier = Modifier
        .alpha(0f)
        .fillMaxWidth(),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//    colors = TextFieldDefaults.textFieldColors(
//        textColor = Color.Transparent,
//        cursorColor = Color.Transparent,
//        containerColor = Color.Transparent,
//        unfocusedIndicatorColor = Color.Transparent,
//        focusedIndicatorColor = Color.Transparent
//    )
)

@Composable
private fun Main(
    length: Int,
    value: String,
    isError: Boolean
) = Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
){
    repeat(length){ i ->
        Box(
            modifier = Modifier
                .width(45.dp)
                .height(54.dp)
                .background(
                    color = when(value.length-1 >= i){
                        true -> blue10
                        false -> Color.Transparent
                    },
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.dp,
                    color = when (value.length - 1 >= i) {
                        true -> Color.Transparent
                        false -> when (isError && value.isEmpty()) {
                            true -> red400
                            false -> blue200
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value.getOrNull(i)?.toString()?: "",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight(400),
                    color = gray900,
                    textAlign = TextAlign.Center,
                )
            )
            if(value.length == i){
                val infinity = rememberInfiniteTransition("")
                val alpha by infinity.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(750, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = ""
                )

                Spacer(modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(0.4f)
                    .background(blue400.copy(alpha))
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewCodeField() = Column(
    modifier = Modifier
        .fillMaxSize(),
    verticalArrangement = Arrangement.Center
){
    val state = remember { mutableStateOf("") }
    CodeField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        length = 6,
        isError = false,
        value = state.value,
        onValueChange = {state.value = it}
    )
    CodeField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        length = 6,
        isError = true,
        value = state.value,
        onValueChange = {state.value = it}
    )
}