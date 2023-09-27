package com.mellowingfactory.wethm.ui.extentions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.mellowingfactory.wethm.ui.theme.WethmTheme
import com.mellowingfactory.wethm.ui.theme.red200

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPiker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    selectedColor: Color,
    size: DpSize = DpSize(128.dp, 212.dp),
    onUpdate: (Int) -> Unit,
    content: @Composable LazyItemScope.(index: Int) -> Unit,
){
    val targetCount = when(count<5){
        true -> 5
        false -> count
    }
    val lazyListState = rememberLazyListState(startIndex)
    val isScrollInProgress = lazyListState.isScrollInProgress
    val visibleItems by remember(lazyListState) {
        derivedStateOf {
            remapVisibleElements(lazyListState, targetCount)
        }
    }

    LaunchedEffect(isScrollInProgress, targetCount) {
        val selected = getSelectedItem(visibleItems)
        if (selected>count-1){
            lazyListState.animateScrollToItem(count-1)
        } else {
            if (!isScrollInProgress) {
                onUpdate(selected)
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .size(size.width, size.height / 5),
            color = selectedColor,
            shape = RoundedCornerShape(10.dp)
        ) {}

        LazyColumn(
            modifier = Modifier
                .height(size.height)
                .width(size.width),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = size.height / 5 * 2),
            flingBehavior = rememberSnapFlingBehavior(
                lazyListState = lazyListState
            )
        ){
            items(targetCount){ index ->
                Box(
                    modifier = Modifier
                        .height(size.height / 5)
                        .width(size.width)
                        .graphicsLayer {
                            alpha = calculateAlpha(
                                visibleItems = visibleItems,
                                currentId = index,
                                isScrollInProgress = isScrollInProgress
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (count>index) content(index)
                    else EmptyItem()
                }
            }
        }
    }
}

@Composable
private fun EmptyItem(){
    Spacer(Modifier.fillMaxSize())
}

private fun calculateAlpha(
    visibleItems: List<Int>,
    currentId: Int,
    isScrollInProgress: Boolean
): Float = with(getSelectedItem(visibleItems)) {
    return when(this-currentId == 0 && !isScrollInProgress){
        true -> 1f
        false -> 0.5f
    }
}

private fun remapVisibleElements(state: LazyListState, count: Int): List<Int> =
    state.visibleItems(100f).map {
        (0 until count).toList()[it.index]
    }
private fun getSelectedItem(visibleItems: List<Int>): Int{
    val firstVisible = visibleItems.first()
    return when(firstVisible == 0){
        true -> when(visibleItems.size){
            3,4 -> visibleItems.size - 3
            else -> 2
        }
        false -> firstVisible + 2
    }
}

private fun LazyListState.visibleItems(itemVisiblePercentThreshold: Float) =
    layoutInfo.visibleItemsInfo
        .filter {
            visibilityPercent(it) >= itemVisiblePercentThreshold
        }

private fun LazyListState.visibilityPercent(info: LazyListItemInfo): Float {
    val cutTop = Integer.max(0, layoutInfo.viewportStartOffset - info.offset)
    val cutBottom = Integer.max(0, info.offset + info.size - layoutInfo.viewportEndOffset)

    return java.lang.Float.max(0f, 100f - (cutTop + cutBottom) * 100f / info.size)
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewWhilePicker() = WethmTheme{
    val list = remember { listOf("Male", "Female", "Other") }
    val current = remember { mutableStateOf(list.first()) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        WheelPiker(
            modifier = Modifier,
            count = list.size,
            selectedColor = red200,
            onUpdate = {
                current.value = list[it]
            }
        ){
            Text(text = list[it])
        }
        Text(
            modifier = Modifier
                .padding(bottom = 128.dp)
                .align(Alignment.BottomCenter),
            text = current.value
        )
    }
}