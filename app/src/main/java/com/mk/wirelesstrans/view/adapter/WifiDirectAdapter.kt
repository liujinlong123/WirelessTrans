package com.mk.wirelesstrans.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.data.bean.WifiDirectItem
import com.mk.wirelesstrans.databinding.WifiDirectAdapterBinding
import com.mk.wirelesstrans.inf.OnListItemClickListener
import com.mk.wirelesstrans.viewmodel.WifiDirectVM

/**
 * @ClassName:      WifiDirectAdapter
 * @Description:    com.mk.wirelesstrans.view.adapter
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 19:19
 */
class WifiDirectAdapter constructor(
    private val dataList: ArrayList<WifiDirectItem>,
    private val model: WifiDirectVM
) : RecyclerView.Adapter<WifiDirectAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "WifiDirectAdapter"
    }

    private var listener: OnListItemClickListener<Int>? = null

    fun setListener(listener: OnListItemClickListener<Int>) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wifi_direct_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.binding.client.setOnClickListener {
            model.wifiDirectItem.value = model.wifiDirectList.value!![position]
            listener?.onItemClick(Constant.SocketType.CLIENT)
        }

        holder.binding.server.setOnClickListener {
            model.wifiDirectItem.value = model.wifiDirectList.value!![position]
            listener?.onItemClick(Constant.SocketType.SERVER)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: WifiDirectAdapterBinding = WifiDirectAdapterBinding.bind(itemView)

        fun bind(item: WifiDirectItem) {
            binding.deviceName.text = item.device?.deviceName
            binding.deviceAddress.text = item.device?.deviceAddress
            binding.deviceState.text = item.state
        }
    }
}