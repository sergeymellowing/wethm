package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.blue400

@Composable
fun WethmCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    useBorder: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) = Box(
    modifier = modifier
        .size(22.dp)
        .border(
            width = 1.dp,
            color = when (useBorder) {
                true -> blue400
                false -> Color.Transparent
            },
            shape = RoundedCornerShape(3.dp)
        )
){
    Icon(
        modifier = Modifier.clickable { onCheckedChange(!checked) },
        imageVector = Icons.Filled.Check,
        tint = getTint(checked, useBorder),
        contentDescription = null
    )
}

private fun getTint(
    checked: Boolean,
    useBorder: Boolean,
): Color = when(checked){
        true -> blue400
        false -> when(useBorder){
            true -> Color.Transparent
            false -> blue400.copy(alpha = 0.3f)
        }
}




@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewWethmCheckBox() = Column{
    Row {
        WethmCheckBox(
            modifier = Modifier.padding(5.dp),
            checked = true,
            onCheckedChange = {}
        )
        WethmCheckBox(
            modifier = Modifier.padding(5.dp),
            checked = false,
            onCheckedChange = {}
        )
    }
    Row {
        WethmCheckBox(
            modifier = Modifier.padding(5.dp),
            checked = true,
            useBorder = false,
            onCheckedChange = {}
        )
        WethmCheckBox(
            modifier = Modifier.padding(5.dp),
            checked = false,
            useBorder = false,
            onCheckedChange = {}
        )
    }
}