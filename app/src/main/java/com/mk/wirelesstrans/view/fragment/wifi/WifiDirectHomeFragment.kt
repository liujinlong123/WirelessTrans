package com.mk.wirelesstrans.view.fragment.wifi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.databinding.WifiHomeFragmentBinding
import com.mk.wirelesstrans.inf.OnListItemClickListener
import com.mk.wirelesstrans.view.activity.MainActivity
import com.mk.wirelesstrans.view.adapter.WifiDirectAdapter
import com.mk.wirelesstrans.view.fragment.BaseFragment
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      WifiDirectHomeFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.wifi
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:44
 */
class WifiDirectHomeFragment : BaseFragment() {
    companion object {
        private const val TAG = "WifiDirectHomeFragment"
    }

    private lateinit var binding: WifiHomeFragmentBinding

    private lateinit var model: WifiDirectVM
    private lateinit var adapter: WifiDirectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as MainActivity).startDiscover()
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
        model = ViewModelProvider(activity as FragmentActivity).get(WifiDirectVM::class.java)
        adapter = WifiDirectAdapter(model.wifiDirectList.value!!, model)
    }

    override fun initView() {
        binding.list.adapter = adapter
    }

    override fun initListener() {
        model.wifiDirectList.observe(viewLifecycleOwner, Observer {
                adapter.notifyDataSetChanged()

                Log.v(TAG, " ------> 我改变了")
            })

        // Item 点击事件
        adapter.setListener(object : OnListItemClickListener<Int> {
            override fun onItemClick(t: Int) {
                when (t) {
                    Constant.SocketType.CLIENT -> {
                        Navigation.findNavController(binding.root).navigate(R.id.home_to_direct_client_fragment)
                    }

                    Constant.SocketType.SERVER -> {
                        Navigation.findNavController(binding.root).navigate(R.id.home_to_direct_server_fragment)
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).stopDiscover()
    }
}