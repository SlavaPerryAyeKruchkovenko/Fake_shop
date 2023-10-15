package com.example.fake_shop.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.fake_shop.FakeShopApp.Companion.APP_ID
import com.example.fake_shop.FakeShopApp.Companion.NOTIFY_CHANEL_ID
import com.example.fake_shop.FakeShopApp.Companion.NOTIFY_CHANEL_NAME
import com.example.fake_shop.MainActivity
import com.example.fake_shop.MainActivity.Companion.FRAGMENT_PARAM
import com.example.fake_shop.R
import com.example.fake_shop.data.NotifyDelay
import com.example.fake_shop.data.models.Product
import com.example.fake_shop.receivers.ProductReceiver
import com.example.fake_shop.ui.product.ProductFragment.Companion.PRODUCT_NAME
import com.example.fake_shop.ui.product.ProductFragment.Companion.PRODUCT_PARAM
import com.example.fake_shop.utils.SDKCheckUtils.sdk24AndUp

object NotifyUtils {
    fun getDelayByRadioBtn(radioBtnId: Int): NotifyDelay {
        return when (radioBtnId) {
            R.id._Fifteen_minutes -> NotifyDelay.FifteenMinutes
            R.id._One_hour -> NotifyDelay.OneHour
            R.id._One_day -> NotifyDelay.OneDay
            R.id._Seven_days -> NotifyDelay.SevenDays
            else -> NotifyDelay.None
        }
    }

    private fun getDelayText(delay: NotifyDelay): String {
        return when (delay) {
            NotifyDelay.FifteenMinutes -> "the notification will be sent in 15 minutes"
            NotifyDelay.OneHour -> "the notification will be sent in 1 hour"
            NotifyDelay.OneDay -> "the notification will be sent in 1 day"
            NotifyDelay.SevenDays -> "the notification will be sent in 7 days"
            else -> throw Exception("Incorrect delay type")
        }
    }

    private fun getDelayTime(delay: NotifyDelay): Long {
        return when (delay) {
            NotifyDelay.FifteenMinutes -> 15 * 60 * 1000L
            NotifyDelay.OneHour -> 60 * 60 * 1000L
            NotifyDelay.OneDay -> 24 * 60 * 60 * 1000L
            NotifyDelay.SevenDays -> 7 * 24 * 60 * 60 * 1000L
            else -> throw Exception("Incorrect delay type")
        }
    }

    fun sendAlarmNotify(
        context: Context,
        manager: NotificationManager,
        notifyDelay: NotifyDelay,
        product: Product
    ): Boolean {
        return sdk24AndUp {
            try {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, ProductReceiver::class.java).apply {
                    putExtra(PRODUCT_PARAM, product.id)
                    putExtra(PRODUCT_NAME, product.title)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    product.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                val delay = getDelayTime(notifyDelay)
                val triggerTime = System.currentTimeMillis() + delay
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("alarm error", e.toString())
                return false
            }
            val isSuccess = createDefaultNotify(
                context,
                manager,
                notifyDelay
            )
            isSuccess
        } ?: false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createDefaultNotify(
        context: Context,
        manager: NotificationManager,
        delay: NotifyDelay
    ): Boolean {
        val chanel = NotificationChannel(
            NOTIFY_CHANEL_ID,
            NOTIFY_CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        return try {
            val notification = Notification.Builder(context, NOTIFY_CHANEL_ID).apply {
                setContentTitle("Notify created")
                setContentText(getDelayText(delay))
                setSmallIcon(R.drawable.notification)
            }.build()
            manager.createNotificationChannel(chanel)
            manager.notify(APP_ID, notification)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("notify error", e.toString())
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createProductNotify(
        context: Context,
        manager: NotificationManager, productId: String, productName: String
    ) {
        val chanel = NotificationChannel(
            NOTIFY_CHANEL_ID,
            NOTIFY_CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra(PRODUCT_PARAM, productId)
                putExtra(FRAGMENT_PARAM, "ProductFragment")
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val notification = Notification.Builder(context, NOTIFY_CHANEL_ID).apply {
                setContentTitle("Let's check your product")
                setContentText("check $productName")
                setContentIntent(pendingIntent)
                setSmallIcon(R.drawable.notification_1)
            }.build()
            manager.createNotificationChannel(chanel)
            manager.notify(APP_ID, notification)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("broadcast notify error", e.toString())
        }
    }
}