package com.mk.wirelesstrans.view.fragment.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mk.wirelesstrans.databinding.DirectConnectFragmentBinding
import com.mk.wirelesstrans.view.fragment.BaseFragment

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DirectConnectFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView() {

    }
}