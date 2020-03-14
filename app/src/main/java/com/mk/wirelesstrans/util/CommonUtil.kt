package com.mk.wirelesstrans.util

import com.mk.wirelesstrans.MyApplication
import java.io.*

object CommonUtil {

    /**
     * 获取字符串
     */
    fun resToString(resId: Int): String {
        return MyApplication.instance.resources.getString(resId)
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    @Throws(IOException::class, UnsupportedEncodingException::class)
    fun readStream(stream: InputStream, maxReadSize: Int = 1024): String {
        val reader: Reader? = InputStreamReader(stream, "UTF-8")
        val rawBuffer = CharArray(maxReadSize)
        val buffer = StringBuffer()
        var readSize: Int = reader?.read(rawBuffer) ?: -1
        var maxReadBytes = maxReadSize
        while (readSize != -1 && maxReadBytes > 0) {
            if (readSize > maxReadBytes) {
                readSize = maxReadBytes
            }
            buffer.append(rawBuffer, 0, readSize)
            maxReadBytes -= readSize
            readSize = reader?.read(rawBuffer) ?: -1
        }
        return buffer.toString()
    }
}