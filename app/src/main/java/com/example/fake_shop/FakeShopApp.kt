package com.example.fake_shop

import android.app.Application
import com.example.fake_shop.di.modules.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FakeShopApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@FakeShopApp)
            modules(appModules)
        }
    }

    companion object {
        const val APP_ID = 2794
        const val NOTIFY_CHANEL_ID = "2641f192-160a-4926-b7b8-b35ba37915d9"
        const val NOTIFY_CHANEL_NAME = "fake_app_notify_chanel"
    }
}