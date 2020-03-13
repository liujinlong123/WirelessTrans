package com.mk.wirelesstrans.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mk.wirelesstrans.view.fragment.bluetooth.BluetoothHomeFragment
import com.mk.wirelesstrans.view.fragment.wifi.WifiDirectHomeFragment

/**
 * @ClassName:      WirelessTransAdapter
 * @Description:    主页ViewPager - Adapter
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:40
 */
class WirelessTransAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        const val WIFI_DIRECT_PAGE_INDEX = 0
        const val BLUETOOTH_PAGE_INDEX = 1
    }

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        WIFI_DIRECT_PAGE_INDEX to { WifiDirectHomeFragment() },
        BLUETOOTH_PAGE_INDEX to { BluetoothHomeFragment() }
    )

    override fun getItemCount(): Int {
        return tabFragmentsCreators.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}