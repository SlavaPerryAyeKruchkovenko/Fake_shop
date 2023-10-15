package com.example.fake_shop.utils

import android.os.Build

object SDKCheckUtils {
    inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else null
    }

    inline fun <T> sdk24AndUp(onSdk24: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onSdk24()
        } else null
    }
}
