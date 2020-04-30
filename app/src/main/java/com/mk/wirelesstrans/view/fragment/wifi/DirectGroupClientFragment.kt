package com.mk.wirelesstrans.view.fragment.wifi

import android.content.Intent
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.databinding.DirectGroupClientFragmentBinding
import com.mk.wirelesstrans.inf.DeviceActionListener
import com.mk.wirelesstrans.service.DataTransService
import com.mk.wirelesstrans.view.activity.MainActivity
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * Wi-Fi Direct Server 负责接收消息
 */
class DirectGroupClientFragment : BaseFragment(), View.OnClickListener {
    companion object {
        private const val TAG = "DirectServerFragment"
    }

    private val model: WifiDirectVM by lazy {
        ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    private lateinit var binding: DirectGroupClientFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectGroupClientFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).disconnect()
    }

    /**
     * 这时候已经连接上了
     */
    override fun initListener() {
        // 界面信息
        model.wifiDirectItem.observe(viewLifecycleOwner, Observer {
            binding.deviceName.text = it.device?.deviceName
            binding.deviceAddress.text = it.device?.deviceAddress
            binding.deviceState.text = it.state
        })

        binding.connect.setOnClickListener(this)
        binding.send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send -> {
                val content = binding.input.text.toString()
                if (content == "") return

                val info = model.wifiP2pInfo.value
                if (info != null && info.groupFormed && info.groupOwnerAddress != null) {
                    val transIntent = Intent(activity, DataTransService::class.java)
                    transIntent.apply {
                        action = Constant.DataTransIntent.ACTION_SEND_DATA
                        putExtra(Constant.DataTransIntent.EXTRAS_STRING, content)
                        putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_ADDRESS, info.groupOwnerAddress.hostAddress)
                        putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_PORT, Constant.SocketType.PORT)
                    }

                    context?.startService(transIntent)
                } else {
                    toast(R.string.wifi_direct_not_ready_yet)
                }
            }

            // 点击连接到服务端
            R.id.connect -> {
                connectDevice(model.wifiDirectItem.value!!.device)
            }

            else -> {}
        }
    }

    /**
     * 作为host发起连接
     */
    private fun connectDevice(device: WifiP2pDevice?) {
        if (device == null) return

        val config = WifiP2pConfig()
        config.deviceAddress = device.deviceAddress
        config.wps.setup = WpsInfo.PBC
        (activity as DeviceActionListener).connect(config)
    }
}