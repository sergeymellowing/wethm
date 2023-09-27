package com.mellowingfactory.wethm.ui.onboardings.user.entities

import androidx.annotation.StringRes
import com.mellowingfactory.wethm.R

enum class Genders(
    @StringRes val label: Int
) {
    MALE(R.string.MALE),
    FEMALE(R.string.FEMALE),
    OTHER(R.string.OTHER)
}