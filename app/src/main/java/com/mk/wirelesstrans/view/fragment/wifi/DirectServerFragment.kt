package com.mk.wirelesstrans.view.fragment.wifi

import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mk.wirelesstrans.databinding.DirectServerFragmentBinding
import com.mk.wirelesstrans.util.CommonUtil
import com.mk.wirelesstrans.view.activity.MainActivity
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM
import java.io.IOException
import java.net.ServerSocket

/**
 * Wi-Fi Direct Server 负责接收消息
 */
class DirectServerFragment : BaseFragment(), WifiP2pManager.ConnectionInfoListener {
    companion object {
        private const val TAG = "DirectServerFragment"
    }

    private val model: WifiDirectVM by lazy {
        ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    private lateinit var binding: DirectServerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectServerFragmentBinding.inflate(inflater, container, false)

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
                DataServerAsyncTask(binding.content).execute()
            }
        })

        // 界面信息
        model.wifiDirectItem.observe(viewLifecycleOwner, Observer {
            binding.deviceName.text = it.device?.deviceName
            binding.deviceAddress.text = it.device?.deviceAddress
            binding.deviceState.text = it.state
        })
    }


    internal class DataServerAsyncTask constructor(private val content: AppCompatTextView) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val serverSocket = ServerSocket(8989)
            try {
                Log.v(TAG, "Server ------> Socket opened")

                val client = serverSocket.accept()
                Log.v(TAG, "Server ------> Connection done")

                val input = client.getInputStream()
                val content = CommonUtil.readStream(input)
                input.close()

                return content
            } catch (e: IOException) {
                e.printStackTrace()
                return ""
            } finally {
                try {
                    serverSocket.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            if (result != null) {
                content.append(result)

                Log.v(TAG, " ------> 这里是返回结果$result")
            }
        }
    }

    override fun onConnectionInfoAvailable(info: WifiP2pInfo?) {

    }
}