package com.mk.wirelesstrans.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mk.wirelesstrans.data.bean.WifiDirectItem

/**
 * @ClassName:      WifiDirectVM
 * @Description:    com.mk.wirelesstrans.viewmodel
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 20:06
 */
class WifiDirectVM : BaseVM() {
    val wifiDirectList = MutableLiveData<ArrayList<WifiDirectItem>>(ArrayList())
}