package com.mk.wirelesstrans.view.fragment.wifi

import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mk.wirelesstrans.MyApplication
import com.mk.wirelesstrans.broadcast.WifiDirectReceiver
import com.mk.wirelesstrans.data.bean.WifiDirectItem
import com.mk.wirelesstrans.databinding.WifiHomeFragmentBinding
import com.mk.wirelesstrans.view.adapter.WifiDirectAdapter
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      WifiDirectHomeFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.wifi
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:44
 */
class WifiDirectHomeFragment : BaseFragment(), WifiP2pManager.ChannelListener {
    companion object {
        private const val TAG = "WifiDirectHomeFragment"
    }

    private lateinit var binding: WifiHomeFragmentBinding
    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var receiver: WifiDirectReceiver

    private lateinit var model: WifiDirectVM
    private lateinit var adapter: WifiDirectAdapter

    private val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WifiHomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
        initView()
        initListener()
    }

    override fun initData() {
        manager = MyApplication.instance.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(context, Looper.getMainLooper(), this)
        channel.also {
            receiver = WifiDirectReceiver(manager, it, this)
        }

        model = ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
        adapter = WifiDirectAdapter(model.wifiDirectList.value!!)
    }

    override fun initView() {
        binding.list.adapter = adapter
    }

    override fun initListener() {
        model.wifiDirectList.observe(viewLifecycleOwner,
            Observer<ArrayList<WifiDirectItem>> {
                adapter.notifyDataSetChanged()

                Log.v(TAG, " ------> 我改变了")
            })
    }

    // ViewPager 切换时会调用 - onPause
    override fun onResume() {
        super.onResume()

        receiver.also {
            context?.registerReceiver(it, intentFilter)
        }

        startDiscover()
    }

    // ViewPager 切换时会调用 - onPause
    override fun onPause() {
        super.onPause()

        stopDiscover()
        receiver.also {
            context?.unregisterReceiver(it)
        }
    }

    private fun startDiscover() {
        manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {

            }

            override fun onFailure(reason: Int) {

            }
        })
    }

    private fun stopDiscover() {
        manager.stopPeerDiscovery(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {

            }

            override fun onFailure(reason: Int) {

            }
        })
    }

    override fun onChannelDisconnected() {

    }
}