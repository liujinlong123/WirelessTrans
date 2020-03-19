package com.mk.wirelesstrans.view.fragment.wifi

import android.content.Intent
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
        model.wifiP2pInfo.observe(viewLifecycleOwner, Observer { info ->
            if (info == null) return@Observer

            if (info.groupFormed && info.isGroupOwner) {
                // DataServerAsyncTask(binding.content).execute()
            }
        })

        // 界面信息
        model.wifiDirectItem.observe(viewLifecycleOwner, Observer {
            binding.deviceName.text = it.device?.deviceName
            binding.deviceAddress.text = it.device?.deviceAddress
            binding.deviceState.text = it.state
        })

        binding.send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.send -> {
                val info = model.wifiP2pInfo.value
                val transIntent = Intent(activity, DataTransService::class.java)
                transIntent.apply {
                    action = Constant.DataTransIntent.ACTION_SEND_DATA
                    putExtra(Constant.DataTransIntent.EXTRAS_STRING, "哼哼哼 我来啦")
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_ADDRESS, "192.168.49.1")
                    putExtra(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_PORT, Constant.SocketType.PORT)
                }

                context?.startService(transIntent)
            }

            else -> {}
        }
    }
}