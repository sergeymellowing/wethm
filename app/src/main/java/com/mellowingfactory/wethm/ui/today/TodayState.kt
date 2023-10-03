package com.mellowingfactory.wethm.ui.today

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.mellowingfactory.wethm.R
import com.mellowingfactory.wethm.ui.theme.blue50
import com.mellowingfactory.wethm.ui.theme.blue_stripes
import com.mellowingfactory.wethm.ui.theme.red500

data class TodayState(
    val status: TodayStatus = TodayStatus.None,
    val vitals: List<VitalAndEnvironment> = listOf(
        VitalAndEnvironment(
            type = "Heart Rate",
            valueStr = "48-77 Bpm",
            iconId = R.drawable.ic_heart,
            iconColor = blue_stripes,
            borderColor = blue50,

            ),
        VitalAndEnvironment(
            type = "Breathing Rate",
            valueStr = "12-19 Bpm",
            iconId = R.drawable.ic_lungs,
            iconColor = blue_stripes,
            borderColor = blue50,
        ),
        VitalAndEnvironment(
            type = "Temperature",
            valueStr = "81.9°F",
            iconId = R.drawable.ic_temperature_max,
            iconColor = red500,
            borderColor = blue50,
        ),
        VitalAndEnvironment(
            type = "Humidity",
            valueStr = "15.2%",
            iconId = R.drawable.ic_humidity_normal,
            iconColor = blue_stripes,
            borderColor = blue50,
        ),
        VitalAndEnvironment(
            type = "Noise",
            valueStr = "12dB",
            iconId = R.drawable.ic_noise_normal,
            iconColor = blue_stripes,
            borderColor = blue50,
        )
    ),
    val hour: String = "2H 18M",
    val currentState: String = "Debt",
    val currentStateColor: Color = red500,

    val weekGraphic: List<Float> = listOf(80F, 70F, 70F, 90F, 70F),
    val todayGraphic: List<Float> = listOf(90F, 90F, 80F, 80F, 70F),

    val todayAvg: Int = weekGraphic.average().toInt(),
    val weekAvgDif: Int = todayGraphic.average().toInt() - weekGraphic.average().toInt(),
)


class VitalAndEnvironment(
    val type: String,
    val valueStr: String,
    @DrawableRes
    val iconId: Int,
    val iconColor: Color,
    val borderColor: Color
)

sealed class TodayStatus(
    val title: String,
    val description: String,
    open val status: String,
    open val time: String
) {
    object Ready : TodayStatus(
        title = "Welcome to wethm",
        status = "READY",
        description = "Your device is connected!\nWe'll have your first sleep data tomorrow.",
        time = "--:-- ~ --:--"
    )

    object None : TodayStatus(
        title = "Detected sleep",
        status = "NONE",
        description = "Your sensor may be off position,\nplease re-adjust the position,\nand make sure it is connected.",
        time = "--:-- ~ --:--"
    )

    object TooShort : TodayStatus(
        title = "Detected sleep",
        status = "TOO SHORT",
        description = "At least 2hrs of sleep is needed for analysis.\nPlease try to sleep longer.",
        time = "--:-- ~ --:--"
    )

    object Analyzing : TodayStatus(
        title = "Detected sleep",
        status = "ANALYZING...",
        description = "Come back later or press the button below\nfor immediate analysis",
        time = "--:-- ~ --:--"
    )

    class Active(
        val info: List<String> = listOf("Excellent", "Restorative", "Efficient"),
        override val status: String = "7H 34M"
    ) : TodayStatus(
        title = "Total Duration",
        description = "",
        time = "12:32 AM  ~  07:44 AM",
        status = status
    )
}