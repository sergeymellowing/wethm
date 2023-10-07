package com.mellowingfactory.wethm.ui.today

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


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


            /**
             * здесь нужна логика загрузки etc
             *  все кейсы есть в dummy
             */
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


