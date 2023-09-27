package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.green400

@Composable
fun WethmButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enable: Boolean,
    text: String,
    color: Color = green400
){
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = enable,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = color.copy(alpha = 0.3f)
        ),
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}