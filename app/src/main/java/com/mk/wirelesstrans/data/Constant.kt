package com.mk.wirelesstrans.data

import android.Manifest

/**
 * @ClassName:      Constant
 * @Description:    com.mk.wirelesstrans.data
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:05
 */
object Constant {

    interface Permission {
        companion object {
            val WIFI = arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)

            // TODO need to update
            val BLUETOOTH = arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)

            val ALL = arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)
        }
    }

    // 主页展示类型
    interface Type {
        companion object {
            const val WIFI = 0
            const val BLUETOOTH = 1
        }
    }

    // DataTransService 传参
    interface DataTransIntent {
        companion object {
            const val ACTION_SEND_DATA = "com.mk.wirelesstrans.wifi.direct.SEND_DATA"
            const val EXTRAS_STRING = "string_content"
            const val EXTRAS_KEY_CODE = "key_code"
            const val EXTRAS_GROUP_OWNER_ADDRESS = "go_host"
            const val EXTRAS_GROUP_OWNER_PORT = "go_port"
        }
    }

    // Socket 类型
    interface SocketType {
        companion object {
            const val CLIENT = 0
            const val SERVER = 1
        }
    }

}