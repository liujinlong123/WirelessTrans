package com.mk.wirelesstrans.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pManager
import androidx.lifecycle.ViewModelProvider
import com.mk.wirelesstrans.data.bean.WifiDirectItem
import com.mk.wirelesstrans.util.CommonUtil
import com.mk.wirelesstrans.view.activity.BaseActivity
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      WifiDirectReceiver
 * @Description:    com.mk.wirelesstrans.broadcast
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 18:13
 */
class WifiDirectReceiver constructor(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: BaseActivity
) : BroadcastReceiver() {

    companion object {
        private const val TAG = "WifiDirectReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        when (intent.action!!) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                when (intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)) {
                    WifiP2pManager.WIFI_P2P_STATE_ENABLED -> {

                    }

                    else -> {

                    }
                }
            }

            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                manager.requestPeers(channel) { p2pDeviceList ->
                    val model = ViewModelProvider(activity)
                        .get(WifiDirectVM::class.java)

                    val dataList = model.wifiDirectList.value!!
                    dataList.clear()
                    for (wifiItem in p2pDeviceList.deviceList) {
                        dataList.add(WifiDirectItem().apply {
                            device = wifiItem
                            state = CommonUtil.resToString(getState(wifiItem.status))
                        })
                    }
                    model.wifiDirectList.value = dataList
                }
            }

            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                val networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO) as NetworkInfo
                if (networkInfo.isConnected) {
                    manager.requestConnectionInfo(channel) {
                        val model = ViewModelProvider(activity)
                            .get(WifiDirectVM::class.java)
                        model.wifiP2pInfo.value = it
                    }
                }
            }

            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {

            }
        }
    }
}