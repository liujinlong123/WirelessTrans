package com.mk.wirelesstrans.view.fragment.bluetooth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mk.wirelesstrans.databinding.BluetoothHomeFragmentBinding
import com.mk.wirelesstrans.view.fragment.BaseFragment

/**
 * @ClassName:      BluetoothHomeFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.bluetooth
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:43
 */
class BluetoothHomeFragment : BaseFragment() {
    companion object {
        private const val TAG = "BluetoothHomeFragment"
    }

    private lateinit var binding: BluetoothHomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BluetoothHomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView() {

    }
}