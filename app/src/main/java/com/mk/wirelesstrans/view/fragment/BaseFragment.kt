package com.mk.wirelesstrans.view.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mk.wirelesstrans.MyApplication

/**
 * @ClassName:      BaseFragment
 * @Description:    com.mk.wirelesstrans.view.fragment
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:03
 */
abstract class BaseFragment : Fragment() {
    open fun initData() {}

    open fun initView() {}

    open fun initListener() {}

    open fun toast(resId: Int) {
        Toast.makeText(MyApplication.instance, resId, Toast.LENGTH_SHORT).show()
    }
}