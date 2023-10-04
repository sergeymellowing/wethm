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
        tempValue = 49,
        humidityValue = 55,
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
    status = TodayStatus.Active(status = "7H 34M",time = "12:32 AM  ~  07:44 AM"),
    hour = "1H 18M",
    currentStatusValue = 40,
    vitals = vitals(
        heartMinValue = 80,
        heartMaxValue = 80,
        breathMinValue = 20,
        breathMaxValue = 30,
        tempValue = 49,
        humidityValue = 55,
        noiseValue = 60
    ),
    weekGraphic = listOf(80F, 90F, 90F, 80F, 80F),
    todayGraphic = listOf(10F, 70F, 60F, 90F, 70F),
)

class TodayViewModel : ViewModel() {

    val state = MutableStateFlow(active)

    init {
        ellie.sleepStages.sleepDuration

        state.value = TodayState(
            /**
             * ?
             */
            currentStatusValue = ellie.sleepDebt[6] * -1,
            vitals = vitals(
                heartMinValue = ellie.heartRate.minValue.average().toInt(),
                heartMaxValue = ellie.heartRate.maxValue.average().toInt(),
                breathMinValue = ellie.breathingRate.minValue.average().toInt(),
                breathMaxValue = ellie.breathingRate.maxValue.average().toInt(),
                tempValue = ellie.temperature.values.average().toInt(),
                humidityValue = ellie.humidity.values.average().toInt(),
                noiseValue = ellie.audio.values.average().toInt(),
            ),


            status = TodayStatus.Active(
                status = ellie.sleepStages.sleepDuration.last().toTime(),
                /**
                 *
                 */
                time = "12:32 AM  ~  07:44 AM"
            ),
            hour = "1H 18M",
            /**
             * ?????
             */
            weekGraphic = ellie.radarValues[6],
            todayGraphic = ellie.radarValues.take(6).map { it.average().toFloat() },
        )
    }

    private fun Int.toTime(): String {
        val hours = this / 60
        val minutes = this % 60
        return "${hours}H ${minutes}M"
    }

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
                is TodayStatus.TooShort -> TodayStatus.Active(status = "7H 34M",time = "12:32 AM  ~  07:44 AM")
            }
        )
    }


}


