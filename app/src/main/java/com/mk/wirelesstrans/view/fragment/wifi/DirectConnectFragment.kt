package com.mk.wirelesstrans.view.fragment.wifi

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
import com.mk.wirelesstrans.databinding.DirectConnectFragmentBinding
import com.mk.wirelesstrans.inf.DeviceActionListener
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      DirectConnectFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.wifi
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 20:54
 */
class DirectConnectFragment : BaseFragment() {

    companion object {
        private const val TAG = "DirectConnectFragment"
    }

    private lateinit var binding: DirectConnectFragmentBinding
    private lateinit var model: WifiDirectVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectConnectFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        initListener()
    }

    override fun initData() {
        model = ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    override fun initView() {

    }

    override fun initListener() {
        model.wifiDirectItem.observe(viewLifecycleOwner, Observer {
            binding.deviceName.text = it.device?.deviceName
            binding.deviceAddress.text = it.device?.deviceAddress
            binding.deviceState.text = it.state

            // 只要一进入该界面
            connectDevice(it.device)
        })
    }

    private fun connectDevice(device: WifiP2pDevice?) {
        if (device == null) return

        val config = WifiP2pConfig()
        config.deviceAddress = device.deviceAddress
        config.wps.setup = WpsInfo.PBC
        (activity as DeviceActionListener).connect(config)
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as DeviceActionListener).disconnect()
    }
}