package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray600

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    error: String = "",
    onValueChange: (String) -> Unit,
    label: String = stringResource(R.string.PASSWORD)
) {
    var isShowPassword by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        WethmTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onValueChange = onValueChange,
            visualTransformation = when(isShowPassword){
                true -> VisualTransformation.None
                false -> PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            error = error,
            label = label,
        )
        TextButton(
            onClick = {
                isShowPassword = !isShowPassword
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)
        ) {
            Text(
                text = when(isShowPassword){
                    true -> stringResource(id = R.string.HIDE)
                    false -> stringResource(id = R.string.SHOW)
                },
                color = gray600
            )
        }
    }
}