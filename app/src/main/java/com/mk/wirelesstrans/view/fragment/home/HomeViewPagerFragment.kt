package com.mk.wirelesstrans.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.databinding.HomeViewpagerFragmentBinding
import com.mk.wirelesstrans.view.adapter.WirelessTransAdapter
import com.mk.wirelesstrans.view.fragment.BaseFragment

/**
 * @ClassName:      HomeViewPagerFragment
 * @Description:    com.mk.wirelesstrans.view.fragment.home
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:18
 */
class HomeViewPagerFragment : BaseFragment() {
    companion object {
        private const val TAG = "HomeViewPagerFragment"
    }

    private lateinit var binding: HomeViewpagerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeViewpagerFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun initView() {
        binding.let {
            it.viewPager.adapter = WirelessTransAdapter(this)

            // Set the icon and text for each tab
            TabLayoutMediator(it.tabs, it.viewPager) { tab, position ->
                tab.setIcon(getTabIcon(position))
                tab.text = getTabTitle(position)
            }.attach()

            (activity as AppCompatActivity).setSupportActionBar(it.toolbar)
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            WirelessTransAdapter.WIFI_DIRECT_PAGE_INDEX -> R.drawable.wifi_tab_selector
            WirelessTransAdapter.BLUETOOTH_PAGE_INDEX -> R.drawable.bluetooth_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            WirelessTransAdapter.WIFI_DIRECT_PAGE_INDEX -> getString(R.string.home_wifi_title)
            WirelessTransAdapter.BLUETOOTH_PAGE_INDEX -> getString(R.string.home_bluetooth_title)
            else -> ""
        }
    }
}