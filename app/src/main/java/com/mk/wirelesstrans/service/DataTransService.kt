package com.mk.wirelesstrans.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.mk.wirelesstrans.data.Constant
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 * 客户端数据传输服务
 */
class DataTransService constructor(
    name: String = "DataTransService"
): IntentService(name) {
    companion object {
        private const val TAG = "DataTransService"

        private const val SOCKET_TIMEOUT = 5000
    }

    override fun onHandleIntent(intent: Intent?) {

        if (intent == null || intent.action == null) return

        if (intent.action.equals(Constant.DataTransIntent.ACTION_SEND_DATA)) {
            val content = intent.extras!!.getString(Constant.DataTransIntent.EXTRAS_STRING)!!
            // val key = intent.extras!!.getInt(Constant.DataTransIntent.EXTRAS_KEY_CODE)
            val host = intent.extras!!.getString(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_ADDRESS)
            val port = intent.extras!!.getInt(Constant.DataTransIntent.EXTRAS_GROUP_OWNER_PORT)

            val socket = Socket()
            var connectedThread: ConnectedThread? = null

            Log.v(TAG, "点击了一下 ------> 准备发送(${host}  ${port})")
            try {
                socket.bind(null)
                socket.connect(InetSocketAddress(host, port), SOCKET_TIMEOUT)
                connectedThread = ConnectedThread(socket)

                connectedThread.write(content)
                connectedThread.start()

                Log.v(TAG, "点击了一下 ------> 发送成功(${host}  ${port})")
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    // Thread.sleep(10000)
                    // connectedThread?.cancel()
                    Log.v("TAG", " ------> 这里关闭了Socket")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}