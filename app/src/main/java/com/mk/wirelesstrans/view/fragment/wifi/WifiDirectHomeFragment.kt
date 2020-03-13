package com.mk.wirelesstrans.view.fragment.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mk.wirelesstrans.databinding.WifiHomeFragmentBinding
import com.mk.wirelesstrans.view.fragment.BaseFragment

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WifiHomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView() {

    }
}