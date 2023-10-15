package com.example.fake_shop.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.fake_shop.FakeShopApp.Companion.APP_ID
import com.example.fake_shop.FakeShopApp.Companion.NOTIFY_CHANEL_ID
import com.example.fake_shop.FakeShopApp.Companion.NOTIFY_CHANEL_NAME
import com.example.fake_shop.R
import com.example.fake_shop.data.NotifyDelay

object NotifyUtils {
    fun getDelayByRadioBtn(radioBtnId: Int): NotifyDelay {
        return when (radioBtnId) {
            R.id._15_minutes -> NotifyDelay.FifteenMinutes
            R.id._1_hour -> NotifyDelay.OneHour
            R.id._1_day -> NotifyDelay.OneDay
            R.id._7_days -> NotifyDelay.SevenDays
            else -> NotifyDelay.None
        }
    }

    fun getDelayText(delay: NotifyDelay): String {
        return when (delay) {
            NotifyDelay.FifteenMinutes -> "the notification will be sent in 15 minutes"
            NotifyDelay.OneHour -> "the notification will be sent in 1 hour"
            NotifyDelay.OneDay -> "the notification will be sent in 1 day"
            NotifyDelay.SevenDays -> "the notification will be sent in 7 days"
            else -> throw Exception("Incorrect delay type")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDefaultNotify(context: Context, manager: NotificationManager, delay: NotifyDelay) {
        val chanel = NotificationChannel(
            NOTIFY_CHANEL_ID,
            NOTIFY_CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        try {
            manager.createNotificationChannel(chanel)
            val notification = Notification.Builder(context, NOTIFY_CHANEL_NAME).apply {
                setContentTitle("Notify created")
                setContentText(getDelayText(delay))
                setSmallIcon(R.drawable.notification)

            }.build()
            manager.notify(APP_ID, notification)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("notify error", e.toString())
        }

    }
}