package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.black
import com.mellowingfactory.wethm.ui.theme.blue400
import com.mellowingfactory.wethm.ui.theme.gray100
import com.mellowingfactory.wethm.ui.theme.gray300
import com.mellowingfactory.wethm.ui.theme.gray50
import com.mellowingfactory.wethm.ui.theme.red400

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WethmTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    error: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Row {
                Text(
                    text = label
                )
                if (error.isNotEmpty() && value.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = error,
                        color = red400
                    )
                }
            }
        },
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Blue,
            containerColor = Color.Transparent,
            focusedIndicatorColor = gray50,
            unfocusedIndicatorColor = gray100,
            unfocusedLabelColor = gray300,
            focusedLabelColor = blue400
        ),
        textStyle = LocalTextStyle.current.copy(color = black),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewWethmTextField() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
){
    val state = remember {
        mutableStateOf("")
    }
    WethmTextField(
        value = state.value,
        onValueChange = {state.value = it},
        modifier = Modifier.fillMaxWidth(),
        label = "Test field"
    )
}