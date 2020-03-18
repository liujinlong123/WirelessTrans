package com.mk.wirelesstrans.view.fragment.wifi

import android.content.Intent
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.databinding.DirectClientFragmentBinding
import com.mk.wirelesstrans.inf.DeviceActionListener
import com.mk.wirelesstrans.service.DataTransService
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      DirectConnectFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.wifi
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 20:54
 */
class DirectClientFragment : BaseFragment(), View.OnClickListener {

    companion object {
        private const val TAG = "DirectConnectFragment"
    }

    private lateinit var binding: DirectClientFragmentBinding
    private val model: WifiDirectVM by lazy {
        ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    private var info: WifiP2pInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectClientFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()
    }

    override fun initListener() {
        model.wifiDirectItem.observe(viewLifecycleOwner, Observer {
            binding.deviceName.text = it.device?.deviceName
            binding.deviceAddress.text = it.device?.deviceAddress
            binding.deviceState.text = it.state
        })

        binding.connect.setOnClickListener(this)
        binding.disconnect.setOnClickListener(this)
        binding.send.setOnClickListener(this)

        // 这时候已经连接上了
        model.wifiP2pInfo.observe(viewLifecycleOwner, Observer { info ->
            this.info = info

            Log.v(TAG, " ------> 这时候连接上了 $info ${info.groupOwnerAddress}")
        })
    }

    /**
     * 连接服务端
     */
    private fun connectDevice(device: WifiP2pDevice?) {
        if (device == null) return

        val config = WifiP2pConfig()
        config.deviceAddress = device.deviceAddress
        config.wps.setup = WpsInfo.PBC
        (activity as DeviceActionListener).connect(config)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // 点击连接到服务端
            R.id.connect -> {
                connectDevice(model.wifiDirectItem.value!!.device)
            }

            // 点击断开连接
            R.id.disconnect -> {
                (activity as DeviceActionListener).disconnect()
            }

            // 点击发送数据
            R.id.send -> {
                if (info == null) return

                val transIntent = Intent(activity, DataTransService::class.java)
                transIntent.apply {
                    action = Constant.DataTransIntent.ACTION_SEND_DATA
                    putExtra(Constant.DataTransIntent.EXTRAS_STRING, "哼哼哼 我来啦")
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_ADDRESS, info?.groupOwnerAddress?.hostAddress)
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_PORT, 8988)
                }

                context?.startService(transIntent)
            }
        }
    }
}