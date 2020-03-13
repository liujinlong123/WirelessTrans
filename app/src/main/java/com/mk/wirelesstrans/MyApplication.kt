package com.mk.wirelesstrans

import android.app.Application

/**
 * @ClassName:      WirelessTrans
 * @Description:    com.mk.wirelesstrans
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:00
 */
class MyApplication : Application() {
    companion object {
        private const val TAG = "MyApplication"

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}