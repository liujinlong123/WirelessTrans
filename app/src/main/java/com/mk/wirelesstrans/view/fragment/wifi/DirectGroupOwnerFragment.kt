package com.mk.wirelesstrans.view.fragment.wifi

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
import com.mk.wirelesstrans.databinding.DirectGroupOwnerFragmentBinding
import com.mk.wirelesstrans.inf.DeviceActionListener
import com.mk.wirelesstrans.inf.HandleWork
import com.mk.wirelesstrans.util.task.DataServerAsyncTask
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      DirectGroupOwnerFragment
 * @Description:    这里是作为Wi-Fi Direct发起连接的host
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 20:54
 */
class DirectGroupOwnerFragment : BaseFragment(), View.OnClickListener, HandleWork {

    companion object {
        private const val TAG = "DirectConnectFragment"
    }

    private lateinit var binding: DirectGroupOwnerFragmentBinding
    private val model: WifiDirectVM by lazy {
        ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    private var info: WifiP2pInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectGroupOwnerFragmentBinding.inflate(inflater, container, false)

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
        binding.prepare.setOnClickListener(this)

        // 这时候已经连接上了
        model.wifiP2pInfo.observe(viewLifecycleOwner, Observer { info ->
            this.info = info

            Log.v(TAG, " ------> 这时候连接上了 $info ${info.groupOwnerAddress}")
        })
    }

    override fun handleWork(t: Any?) {
        val content = t!!.toString()
        binding.content.append(content)
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
                /*if (info == null) return

                val transIntent = Intent(activity, DataTransService::class.java)
                transIntent.apply {
                    action = Constant.DataTransIntent.ACTION_SEND_DATA
                    putExtra(Constant.DataTransIntent.EXTRAS_STRING, "哼哼哼 我来啦")
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_ADDRESS, info?.groupOwnerAddress?.hostAddress)
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_PORT, Constant.SocketType.PORT)
                }

                context?.startService(transIntent)*/
            }

            // 准备接收数据
            R.id.prepare -> {
                DataServerAsyncTask(this).execute()
            }
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