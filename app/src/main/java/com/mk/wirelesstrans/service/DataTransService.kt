package com.mk.wirelesstrans.service

import android.app.IntentService
import android.content.Intent
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

            try {
                socket.bind(null)
                socket.connect(InetSocketAddress(host, port), SOCKET_TIMEOUT)

                val output = socket.getOutputStream()
                output.write(content.toByteArray())
                output.close()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (socket.isConnected) {
                    try {
                        socket.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}