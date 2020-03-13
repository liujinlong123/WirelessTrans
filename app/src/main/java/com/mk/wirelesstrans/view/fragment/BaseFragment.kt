package com.mk.wirelesstrans.view.fragment

import androidx.fragment.app.Fragment

/**
 * @ClassName:      BaseFragment
 * @Description:    com.mk.wirelesstrans.view.fragment
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:03
 */
abstract class BaseFragment : Fragment() {
    fun initData() {}

    abstract fun initView()
}