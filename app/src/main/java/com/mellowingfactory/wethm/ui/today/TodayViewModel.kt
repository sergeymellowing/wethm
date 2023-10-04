package com.mellowingfactory.wethm.ui.today

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue400
import com.mellowingfactory.wethm.ui.theme.blue50
import com.mellowingfactory.wethm.ui.theme.blue_stripes
import com.mellowingfactory.wethm.ui.theme.green300
import com.mellowingfactory.wethm.ui.theme.red100
import com.mellowingfactory.wethm.ui.theme.red500
import kotlinx.coroutines.flow.MutableStateFlow


private val ready = TodayState(
    status = TodayStatus.Ready,
    hour = "",
    vitals = emptyList(),
    currentStatusValue = 0,
    weekGraphic = listOf(0F, 0F, 0F, 0F, 0F),
    todayGraphic = listOf(0F, 0F, 0F, 0F, 0F),
)

private val none = TodayState(
    status = TodayStatus.None,
    hour = "",
    currentStatusValue = 0,
    vitals = emptyList(),
    weekGraphic = listOf(80F, 90F, 90F, 60F, 80F),
    todayGraphic = listOf(0F, 0F, 0F, 0F, 0F),
    todayAvgStr = "--",
)

private val tooShort = TodayState(
    status = TodayStatus.TooShort(),
    hour = "1H 18M",
    currentStatusValue = 40,
    vitals = vitals(
        heartMinValue = 80,
        heartMaxValue = 80,
        breathMinValue = 20,
        breathMaxValue = 30,
        tempValue = 49F,
        humidityValue = 55F,
        noiseValue = 60
    ),
    weekGraphic = listOf(80F, 90F, 90F, 60F, 80F),
    todayGraphic = listOf(0F, 0F, 0F, 0F, 0F),
    todayAvgStr = "--",
)


private val analyzing = TodayState(
    status = TodayStatus.Analyzing,
    hour = "",
    vitals = emptyList(),
    weekGraphic = listOf(80F, 90F, 90F, 60F, 80F),
    todayGraphic = listOf(0F, 0F, 0F, 0F, 0F),
    todayAvgStr = "--",
    currentStatusValue = 0,
)

private val active = TodayState(
    status = TodayStatus.Active(),
    hour = "1H 18M",
    currentStatusValue = 40,
    vitals = emptyList(),
    weekGraphic = listOf(80F, 90F, 90F, 80F, 80F),
    todayGraphic = listOf(10F, 70F, 60F, 90F, 70F),
)

class TodayViewModel : ViewModel() {

    val state = MutableStateFlow(tooShort)

//    status = TodayStatus.Ready,
//    hour = "2H 18M",
//    currentStatus = currentStatus(12),
//    currentStatusColor = currentStatusColor(12),
//    vitals = vitals(
//    heartMinValue = 5,
//    heartMaxValue = 1,
//    breathMinValue = 2,
//    breathMaxValue = 3,
//    tempValue = 4f,
//    humidityValue = 5f,
//    noiseValue = 6
//    ),
//    weekGraphic = listOf(80F, 70F, 70F, 90F, 70F),
//    todayGraphic = listOf(90F, 90F, 80F, 80F, 70F),


    /**
     * Test
     */
    fun changeState() {
        state.value = state.value.copy(
            status = when (state.value.status) {
                is TodayStatus.Active -> TodayStatus.Analyzing
                TodayStatus.Analyzing -> TodayStatus.None
                TodayStatus.None -> TodayStatus.Ready
                is TodayStatus.Ready -> TodayStatus.TooShort()
                is TodayStatus.TooShort -> TodayStatus.Active()
            }
        )
    }


}


