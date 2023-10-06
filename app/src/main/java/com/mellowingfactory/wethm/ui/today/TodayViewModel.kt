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
    weekGraphic = listOf(0, 0, 0, 0, 0),
    todayGraphic = listOf(0, 0, 0, 0, 0),
    todayAvg = 10
)

private val none = TodayState(
    status = TodayStatus.None,
    hour = "",
    currentStatusValue = 0,
    vitals = emptyList(),
    weekGraphic = listOf(80, 90, 90, 60, 80),
    todayGraphic = listOf(0, 0, 0, 0, 0),
    todayAvgStr = "--",
    todayAvg = 10
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
    weekGraphic = listOf(80, 90, 90, 60, 80),
    todayGraphic = listOf(0, 0, 0, 0, 0),
    todayAvgStr = "--",
    todayAvg = 10
)


private val analyzing = TodayState(
    status = TodayStatus.Analyzing,
    hour = "",
    vitals = emptyList(),
    weekGraphic = listOf(80, 90, 90, 60, 80),
    todayGraphic = listOf(0, 0, 0, 0, 0),
    todayAvgStr = "--",
    currentStatusValue = 0,
    todayAvg = 10
)

private val active = TodayState(
    status = TodayStatus.Active(status = "7H 34M", time = "12:32 AM  ~  07:44 AM"),
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
    weekGraphic = listOf(80, 90, 90, 80, 80),
    todayGraphic = listOf(10, 70, 60, 90, 70),
    todayAvg = 10
)

class TodayViewModel : ViewModel() {

    val state = MutableStateFlow(active)

    init {

        val weekGraphic = IntArray(5) { 0 }
        ellie.radarValues.take(7).forEach {
            it.forEachIndexed { index, it ->
                weekGraphic[index] = weekGraphic[index] + it
            }
        }
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
                 * ????
                 */
                time = "${
                    ellie.sleepStages.sleepStart.last().toInt().toTime12Format()
                }  ~  ${ellie.sleepStages.sleepEnd.last().toInt().toTime12Format()}"
            ),
            /**
             *
             */
            hour = ellie.sleepQuality.average().toInt().toTime(),
            /**
             * ?????
             */
            weekGraphic = weekGraphic.map { it / 7 },
            todayGraphic = ellie.radarValues.last(),
            todayAvg = ellie.sleepQuality.last()
        )
    }

    private fun Int.toTime(): String {
        val hours = this / 60
        val minutes = this % 60
        return "${hours}H ${minutes}M"
    }

    private fun Int.toTime12Format(addTimeZone: Int = 9): String {
        val current = this + 60 * addTimeZone
        val hours = current / 60 % 12
        val minutes = current % 60
        return "${hours.toHour()}:${minutes.toHour()} " + if (current / 60 < 13) "AM" else "PM"
    }

    private fun Int.toHour(): String {
        return if (this < 10) "0${this}" else this.toString()
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
                is TodayStatus.TooShort -> TodayStatus.Active(
                    status = "7H 34M",
                    time = "12:32 AM  ~  07:44 AM"
                )
            }
        )
    }


}


