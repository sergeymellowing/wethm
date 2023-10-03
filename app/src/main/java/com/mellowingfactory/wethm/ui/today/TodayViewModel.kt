package com.mellowingfactory.wethm.ui.today

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class TodayViewModel : ViewModel() {

    val state = MutableStateFlow(TodayState())


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


}