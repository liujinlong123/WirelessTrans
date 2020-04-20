package com.mk.wirelesstrans.view.activity

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mk.wirelesstrans.MyApplication
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.broadcast.WifiDirectReceiver
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.databinding.ActivityMainBinding
import com.mk.wirelesstrans.inf.DeviceActionListener
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

class MainActivity : BaseActivity(), DeviceActionListener, WifiP2pManager.ChannelListener {
    companion object {
        private const val TAG = "MainActivity"
    }

    // Wi-Fi Direct
    private lateinit var wifiModel: WifiDirectVM
    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var wifiDirectReceiver: WifiDirectReceiver
    private val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )

        initData()

        registerReceiver(wifiDirectReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(wifiDirectReceiver)
    }

    private fun initData() {
        // Wi-Fi
        wifiModel = ViewModelProvider(this).get(WifiDirectVM::class.java)
        manager = MyApplication.instance.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, Looper.getMainLooper(), this)
        channel.also {
            wifiDirectReceiver = WifiDirectReceiver(manager, it, this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.home).navigateUp()
    }

    fun startDiscover(type: Int = Constant.Type.WIFI) {
        when (type) {
            Constant.Type.WIFI -> {
                manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Log.v(TAG, " ------> Wi-Fi 搜索成功")
                    }

                    override fun onFailure(reason: Int) {
                        Log.v(TAG, " ------> Wi-Fi 搜索失败")
                    }
                })
            }

            Constant.Type.BLUETOOTH -> {

            }
        }
    }

    fun stopDiscover(type: Int = Constant.Type.WIFI) {
        when (type) {
            Constant.Type.WIFI -> {
                manager.stopPeerDiscovery(channel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Log.v(TAG, " ------> Wi-Fi 停止搜索成功")
                    }

                    override fun onFailure(reason: Int) {
                        Log.v(TAG, " ------> Wi-Fi 停止搜索失败")
                    }
                })
            }

            Constant.Type.BLUETOOTH -> {

            }
        }
    }

    /**
     * 显示详细信息
     */
    override fun showDetails(device: WifiP2pDevice) {

    }

    /**
     * 取消连接
     */
    override fun cancelDisconnect() {
        val device = wifiModel.wifiDirectItem.value?.device ?: return

        if (device.status == WifiP2pDevice.CONNECTED)
            disconnect()
        else if (device.status == WifiP2pDevice.AVAILABLE ||
                device.status == WifiP2pDevice.INVITED){
            manager.cancelConnect(channel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    toast("Aborting connection")
                }

                override fun onFailure(reason: Int) {
                    toast("Connect abort request failed. Reason Code: $reason")
                }
            })
        }
    }

    /**
     * 连接设备
     */
    override fun connect(config: WifiP2pConfig) {
        manager.connect(channel, config, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.v(TAG, " ------> 连接成功dis")
            }

            override fun onFailure(reason: Int) {
                toast("Connect failed. Retry.")
                Log.v(TAG, " ------> 连接失败")
            }
        })
    }

    /**
     * 断开连接
     */
    override fun disconnect() {
        manager.removeGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.v(TAG, " ------> 断开连接成功")
            }

            override fun onFailure(reason: Int) {
                toast("Disconnect failed. Reason: $reason")
                Log.v(TAG, " ------> 断开连接失败")
            }
        })
    }

    override fun onChannelDisconnected() {
        // We will try once more
        Log.v(TAG, " ------> 连接断开")
    }

    override fun createGroupOwner() {
        manager.createGroup(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.v(TAG, " ------> 创建成功")
            }

            override fun onFailure(reason: Int) {
                Log.v(TAG, " ------> 创建失败")
            }
        })
    }
}
