package com.mellowingfactory.wethm.ui.today

import androidx.lifecycle.ViewModel
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue50
import com.mellowingfactory.wethm.ui.theme.blue_stripes
import com.mellowingfactory.wethm.ui.theme.red100
import com.mellowingfactory.wethm.ui.theme.red500
import kotlinx.coroutines.flow.MutableStateFlow

class TodayViewModel : ViewModel() {

    val state = MutableStateFlow(
        TodayState(
            hour = "2H 18M",
            currentState = "Debt",
            currentStateColor = red500,
            vitals = vitals(
                heartMinValue = 5,
                heartMaxValue = 1,
                breathMinValue = 2,
                breathMaxValue = 3,
                tempValue = 4f,
                humidityValue = 5f,
                noiseValue = 6
            ),
            weekGraphic = listOf(80F, 70F, 70F, 90F, 70F),
            todayGraphic = listOf(90F, 90F, 80F, 80F, 70F)
        )
    )


    /**
     * Test
     */
    fun changeState() {
        state.value = state.value.copy(
            status = when (state.value.status) {
                is TodayStatus.Active -> TodayStatus.Analyzing
                TodayStatus.Analyzing -> TodayStatus.None
                TodayStatus.None -> TodayStatus.Ready
                TodayStatus.Ready -> TodayStatus.TooShort
                TodayStatus.TooShort -> TodayStatus.Active()
            }
        )
    }


    private fun vitals(
        heartMinValue: Int,
        heartMaxValue: Int,
        breathMinValue: Int,
        breathMaxValue: Int,
        tempValue: Float,
        humidityValue: Float,
        noiseValue: Int,
    ): List<VitalAndEnvironment> {


        val heartColor = if (heartMinValue < 40 || heartMaxValue > 140) red500 else blue_stripes
        val heartBorderColor = if (heartMinValue < 40 || heartMaxValue > 140) red100 else blue50

        val heart = VitalAndEnvironment(
            typeRes = R.string.HEART_RATE,
            valueStr = "${heartMinValue}-${heartMaxValue} Bpm",
            iconId = R.drawable.ic_heart,
            iconColor = heartColor,
            borderColor = heartBorderColor,
        )

        val breathColor = if (breathMinValue < 40 || breathMaxValue > 140) red500 else blue_stripes
        val breathBorderColor = if (breathMinValue < 4 || breathMaxValue > 30) red100 else blue50


        val breathing = VitalAndEnvironment(
            typeRes = R.string.BREATHING_RATE,
            valueStr = "${breathMinValue}-${breathMaxValue} Bpm",
            iconId = R.drawable.ic_lungs,
            iconColor = breathColor,
            borderColor = breathBorderColor,
        )


        val tempColor = if (tempValue < 20 || tempValue > 23) red500 else blue_stripes
        val tempBorderColor = if (tempValue < 20 || tempValue > 24) red100 else blue50
        val tempIcon =
            if (tempValue < 20) R.drawable.ic_temperature_min
            else if (tempValue > 24) R.drawable.ic_temperature_max
            else R.drawable.ic_temperature_normal

        val temperature = VitalAndEnvironment(
            typeRes = R.string.TEMPERATURE,
            valueStr = "${tempValue}°F",
            iconId = tempIcon,
            iconColor = tempColor,
            borderColor = tempBorderColor,
        )

        val humidityColor = if (humidityValue < 30 || humidityValue > 50) red500 else blue_stripes
        val humidityBorderColor = if (humidityValue < 30 || humidityValue > 50) red100 else blue50
        val humidityIcon =
            if (humidityValue < 30) R.drawable.ic_humidity_min
            else if (humidityValue > 50) R.drawable.ic_humidity_max
            else R.drawable.ic_humidity_normal
        val humidity = VitalAndEnvironment(
            typeRes = R.string.HUMIDITY,
            valueStr = "${humidityValue}%",
            iconId = humidityIcon,
            iconColor = humidityColor,
            borderColor = humidityBorderColor,
        )

        val noiseColor = if (noiseValue < 30 || noiseValue > 50) red500 else blue_stripes
        val noiseBorderColor = if (noiseValue < 30 || noiseValue > 30) red100 else blue50
        val noiseIcon =
            if (noiseValue < 30) R.drawable.ic_noise_min
            else if (noiseValue > 50) R.drawable.ic_noise_max
            else R.drawable.ic_noise_normal
        val noise = VitalAndEnvironment(
            typeRes = R.string.NOISE,
            valueStr = "${noiseValue}dB",
            iconId = noiseIcon,
            iconColor = noiseColor,
            borderColor = noiseBorderColor,
        )

        return listOf(
            heart,
            breathing,
            temperature,
            humidity,
            noise
        )
    }

}