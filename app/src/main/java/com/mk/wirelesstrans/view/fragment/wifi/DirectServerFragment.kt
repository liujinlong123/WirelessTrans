package com.mk.wirelesstrans.view.fragment.wifi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mk.wirelesstrans.databinding.DirectServerFragmentBinding
import com.mk.wirelesstrans.view.fragment.BaseFragment

class DirectServerFragment : BaseFragment() {
    companion object {
        private const val TAG = "DirectServerFragment"
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

    override fun initView() {

    }
}