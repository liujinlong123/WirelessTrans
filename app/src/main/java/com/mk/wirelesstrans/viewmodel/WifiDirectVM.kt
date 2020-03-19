package com.mk.wirelesstrans.viewmodel

import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pGroup
import android.net.wifi.p2p.WifiP2pInfo
import androidx.lifecycle.MutableLiveData
import com.mk.wirelesstrans.data.bean.WifiDirectItem

/**
 * @ClassName:      WifiDirectVM
 * @Description:    com.mk.wirelesstrans.viewmodel
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 20:06
 */
class WifiDirectVM : BaseVM() {
    val wifiDirectList = MutableLiveData<ArrayList<WifiDirectItem>>(ArrayList())

    val wifiDirectItem = MutableLiveData<WifiDirectItem>()

    // WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
    val wifiP2pInfo = MutableLiveData<WifiP2pInfo>()
    val networkInfo = MutableLiveData<NetworkInfo>()
    val wifiP2pGroup = MutableLiveData<WifiP2pGroup>()

    // WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
    val wifiP2pDevice = MutableLiveData<WifiP2pDevice>()
}