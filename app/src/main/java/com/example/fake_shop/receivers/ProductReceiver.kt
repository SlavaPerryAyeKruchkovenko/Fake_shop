package com.example.fake_shop.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.fake_shop.ui.product.ProductFragment.Companion.PRODUCT_NAME
import com.example.fake_shop.ui.product.ProductFragment.Companion.PRODUCT_PARAM
import com.example.fake_shop.utils.NotifyUtils.createProductNotify

class ProductReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val productId = intent?.getStringExtra(PRODUCT_PARAM) ?: return
        val productName = intent.getStringExtra(PRODUCT_NAME) ?: return
        if (context != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createProductNotify(context, manager, productId, productName)
        }
    }
}