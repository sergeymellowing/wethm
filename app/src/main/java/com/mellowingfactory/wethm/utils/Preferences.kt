package com.mellowingfactory.wethm.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getString(key: String):String{
        return preferences.getString(key,"").toString()
    }

    fun setString(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getBoolean(
        key: String,
        default: Boolean = false
    ):Boolean {
        return preferences.getBoolean(key, default)
    }

    fun setBoolean(key: String, value: Boolean){
        preferences.edit().putBoolean(key, value).apply()
    }

    companion object{
        const val IS_FIRST_START = "IsFirstStart"
        const val EMAIL = "Email"
        const val AUTO_LOGIN ="AutoLogin"
        const val REMEMBER_EMAIL ="RememberEmail"
    }
}