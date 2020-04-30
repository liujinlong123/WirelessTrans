package com.mk.wirelesstrans.service

import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

/**
 * @ClassName:      WirelessTrans
 * @Description:    com.mk.wirelesstrans.service
 * @Author:         Aiden.liu
 * @CreateDate:     2020/04/30 15:02
 */
class ConnectedThread constructor(private val mmSocket: Socket) : Thread() {
    companion object {
        const val TAG = "ConnectedThread"
    }

    private val inputStream by lazy {
        var tmpIn: InputStream? = null
        try {
            tmpIn = mmSocket.getInputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        tmpIn
    }

    private val outputStream by lazy {
        var tmpOut: OutputStream? = null
        try {
            tmpOut = mmSocket.getOutputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        tmpOut
    }

    private var mmBuffer: ByteArray? = null

    override fun run() {
        mmBuffer = ByteArray(1024)
        var numBytes: Int
        while (true) {
            try {
                numBytes = inputStream!!.read(mmBuffer!!)

                Log.v(TAG, " ------> 正在读取 size：$numBytes")
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }

    fun write(content: String) {
        try {
            outputStream?.write(content.toByteArray())
            Log.v(TAG, " ------> 写完了")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun cancel() {
        try {
            inputStream?.close()
            outputStream?.close()
            mmSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}