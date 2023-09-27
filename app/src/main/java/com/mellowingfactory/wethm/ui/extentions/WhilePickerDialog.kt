package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.gray100
import com.mellowingfactory.wethm.ui.theme.green600
import com.mellowingfactory.wethm.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhilePickerDialog(
    isOpen: MutableState<Boolean>,
    list: List<String>,
    suffix: String,
    onSelect: (String) -> Unit
){
    val stateSheet = rememberModalBottomSheetState()
    if (isOpen.value){
        ModalBottomSheet(
            modifier = Modifier.navigationBarsPadding(),
            containerColor = white,
            onDismissRequest = { isOpen.value = false },
            dragHandle = {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                ){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 24.dp)
                            .clickable {
                                isOpen.value = false
                            },
                        text = stringResource(R.string.CONFIRM),
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight(500),
                            color = green600
                        )
                    )
                }
            },
            sheetState = stateSheet
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                WheelPiker(
                    startIndex = list.size/2,
                    count = list.size,
                    selectedColor = gray100,
                    onUpdate = { onSelect(list[it]) }
                ) {
                    Text(text = "${list[it]} $suffix")
                }
            }
        }

    }
}