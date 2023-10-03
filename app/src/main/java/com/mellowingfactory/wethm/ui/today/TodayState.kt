package com.mellowingfactory.wethm.ui.today

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.mellowingfactory.wethm.R

data class TodayState(
    val status: TodayStatus = TodayStatus.None,
    val vitals: List<VitalAndEnvironment>,
    val hour: String,
    val currentState: String,
    val currentStateColor: Color,
    val weekGraphic: List<Float>,
    val todayGraphic: List<Float>,

    val todayAvg: Int = weekGraphic.average().toInt(),
    val weekAvgDif: Int = todayGraphic.average().toInt() - weekGraphic.average().toInt(),
)


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

    object TooShort : TodayStatus(
        title = R.string.TODAY_TIME_N_TOKENS_TITLE,
        status = "TOO SHORT",
        description = R.string.TODAY_TIME_N_TOKENS_INFO3,
        time = "--:-- ~ --:--"
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