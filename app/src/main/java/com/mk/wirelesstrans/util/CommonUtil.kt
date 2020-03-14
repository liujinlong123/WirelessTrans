package com.mk.wirelesstrans.util

import com.mk.wirelesstrans.MyApplication

object CommonUtil {

    /**
     * 获取字符串
     */
    fun resToString(resId: Int): String {
        return MyApplication.instance.resources.getString(resId)
    }
}