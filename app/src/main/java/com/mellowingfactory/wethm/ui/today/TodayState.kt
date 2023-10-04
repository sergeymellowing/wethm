package com.mellowingfactory.wethm.ui.today

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue400
import com.mellowingfactory.wethm.ui.theme.blue50
import com.mellowingfactory.wethm.ui.theme.blue500
import com.mellowingfactory.wethm.ui.theme.blue_stripes
import com.mellowingfactory.wethm.ui.theme.green300
import com.mellowingfactory.wethm.ui.theme.red100
import com.mellowingfactory.wethm.ui.theme.red500
import com.mellowingfactory.wethm.ui.theme.yellow500

data class TodayState(
    val status: TodayStatus,
    val vitals: List<VitalAndEnvironment>,
    val hour: String,
    val currentStatusValue: Int,
    val currentStatus: Int = currentStatus(currentStatusValue),
    val currentStatusColor: Color = currentStatusColor(currentStatusValue),
    val weekGraphic: List<Float>,

    val todayGraphic: List<Float>,

    val todayAvg: Int = weekGraphic.average().toInt(),
    val todayAvgStr: String = weekGraphic.average().toInt().toString(),
    val todayGraphicColor: Color = todayColor(todayAvg),
    val weekAvgDif: Int = todayGraphic.average().toInt() - weekGraphic.average().toInt(),
)

private fun todayColor(value: Int): Color {
    return if (value > 79) {
        blue500.copy(alpha = 0.33f)
    } else if (value > 60) {
        yellow500.copy(alpha = 0.33f)
    } else {
        red500.copy(alpha = 0.33f)
    }
}


private fun currentStatusColor(value: Int): Color {
    return if (value < -30) red500
    else if (value > 30) green300
    else blue400
}


private fun currentStatus(value: Int): Int {
    return if (value < -30) R.string.DEBT
    else if (value > 30) R.string.SURPLUS
    else R.string.OPTIMAL
}


fun vitals(
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

    val breathColor = if (breathMinValue < 4 || breathMaxValue > 30) red500 else blue_stripes
    val breathBorderColor = if (breathMinValue < 4 || breathMaxValue > 30) red100 else blue50


    val breathing = VitalAndEnvironment(
        typeRes = R.string.BREATHING_RATE,
        valueStr = "${breathMinValue}-${breathMaxValue} Bpm",
        iconId = R.drawable.ic_lungs,
        iconColor = breathColor,
        borderColor = breathBorderColor,
    )


    val tempColor = if (tempValue < 20 || tempValue > 24) red500 else blue_stripes
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


class VitalAndEnvironment(
    @StringRes
    val typeRes: Int,
    val valueStr: String,
    @DrawableRes
    val iconId: Int,
    val iconColor: Color,
    val borderColor: Color
)

sealed class TodayStatus(
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
    open val status: String,
    open val time: String
) {
    object Ready : TodayStatus(
        title = R.string.TODAY_TIME_N_TOKENS_TITLE2,
        status = "READY",
        description = R.string.TODAY_TIME_N_TOKENS_INFO2,
        time = "--:-- ~ --:--"
    )

    object None : TodayStatus(
        title = R.string.TODAY_TIME_N_TOKENS_TITLE,
        status = "NONE",
        description = R.string.TODAY_TIME_N_TOKENS_INFO,
        time = "--:-- ~ --:--"
    )

    class TooShort(override val time: String = "5:12 am  ~  07:39am") : TodayStatus(
        title = R.string.TODAY_TIME_N_TOKENS_TITLE,
        status = "TOO SHORT",
        description = R.string.TODAY_TIME_N_TOKENS_INFO3,
        time = time
    )

    object Analyzing : TodayStatus(
        title = R.string.TODAY_TIME_N_TOKENS_TITLE,
        status = "ANALYZING...",
        description = R.string.TODAY_TIME_N_TOKENS_TITLE3,
        time = "--:-- ~ --:--"
    )

    class Active(
        val info: List<Int> = listOf(
            R.string.TOKEN_EXCELLENT,
            R.string.TOKEN_RESTORATIVE,
            R.string.TOKEN_EFFICIENT
        ),
        override val status: String = "7H 34M"
    ) : TodayStatus(
        title = R.string.TOTAL_DURATION,
        description = R.string.EMPTY,
        time = "12:32 AM  ~  07:44 AM",
        status = status
    )
}