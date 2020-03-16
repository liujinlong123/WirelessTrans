package com.mk.wirelesstrans.view.fragment.wifi

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
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM
import java.io.IOException
import java.net.ServerSocket

/**
 * Wi-Fi Direct Server 负责接收消息
 */
class DirectServerFragment : BaseFragment() {
    companion object {
        private const val TAG = "DirectServerFragment"
    }

    private lateinit var model: WifiDirectVM
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

        initData()
        initListener()
    }

    override fun initData() {
        model = ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
    }

    override fun initView() {

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
    }


    internal class DataServerAsyncTask constructor(private val content: AppCompatTextView) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val serverSocket = ServerSocket(8988)
            try {
                Log.v(TAG, "Server ------> Socket opened")

                val client = serverSocket.accept()
                Log.v(TAG, "Server ------> Connection done")

                val input = client.getInputStream()

                return CommonUtil.readStream(input)
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
            }
        }

    }
}