package com.mk.wirelesstrans.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
    }
}
