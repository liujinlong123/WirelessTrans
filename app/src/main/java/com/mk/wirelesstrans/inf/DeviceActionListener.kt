package com.mk.wirelesstrans.inf

import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice

interface DeviceActionListener {

    fun showDetails(device: WifiP2pDevice)

    fun cancelDisconnect()

    fun connect(config: WifiP2pConfig)

    fun disconnect()
}