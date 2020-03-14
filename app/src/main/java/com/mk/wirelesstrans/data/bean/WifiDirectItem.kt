package com.mk.wirelesstrans.data.bean

import android.net.wifi.p2p.WifiP2pDevice
import com.mk.wirelesstrans.R

/**
 * @ClassName:      WirelessTrans
 * @Description:    com.mk.wirelesstrans.data.bean
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 19:22
 */
class WifiDirectItem {
    var device: WifiP2pDevice? = null
    var state: String = ""

    fun getState(state: Int): Int {
        return when (state) {
            WifiP2pDevice.AVAILABLE -> R.string.wifi_direct_device_available
            WifiP2pDevice.INVITED -> R.string.wifi_direct_device_invited
            WifiP2pDevice.CONNECTED -> R.string.wifi_direct_device_connected
            WifiP2pDevice.FAILED -> R.string.wifi_direct_device_unavailable
            else -> R.string.wifi_direct_device_unknown
        }
    }
}