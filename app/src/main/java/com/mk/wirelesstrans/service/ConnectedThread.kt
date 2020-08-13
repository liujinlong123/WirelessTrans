package com.mk.wirelesstrans.service

import android.util.Log
import java.io.*
import java.net.Socket
import java.nio.charset.StandardCharsets

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

    override fun run() {
        try {
            val content = readStream(inputStream!!)

            Log.v(TAG, " ------> 收到消息 $content")

            mmSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
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

    /**
     * Converts the contents of an InputStream to a String.
     */
    @Throws(IOException::class)
    private fun readStream(stream: InputStream, maxReadSize: Int = 1024): String? {
        var maxSize = maxReadSize
        val reader: Reader
        reader = InputStreamReader(stream, StandardCharsets.UTF_8)
        val rawBuffer = CharArray(maxReadSize)
        var readSize: Int
        val buffer = StringBuilder()
        while (reader.read(rawBuffer).also { readSize = it } != -1 && maxReadSize > 0) {
            Log.v(TAG, " ------> 一次也没有读到吗")

            if (readSize > maxReadSize) {
                readSize = maxReadSize
            }
            buffer.append(rawBuffer, 0, readSize)
            maxSize -= readSize
            break
        }
        return buffer.toString()
    }
}