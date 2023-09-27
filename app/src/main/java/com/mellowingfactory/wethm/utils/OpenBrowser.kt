package com.mellowingfactory.wethm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

fun openBrowser(url: String, context: Context){
    try {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        context.startActivity(urlIntent)
    } catch (e: Exception){
        Log.e("Wethm", "Browser don't open: $e")
    }
}