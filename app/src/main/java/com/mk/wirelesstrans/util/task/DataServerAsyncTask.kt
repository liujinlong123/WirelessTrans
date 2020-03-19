package com.mk.wirelesstrans.util.task

import android.os.AsyncTask
import android.util.Log
import com.mk.wirelesstrans.data.Constant
import com.mk.wirelesstrans.inf.HandleWork
import com.mk.wirelesstrans.util.CommonUtil
import java.io.IOException
import java.net.ServerSocket

class DataServerAsyncTask constructor(
    private val workInf: HandleWork
) : AsyncTask<Void, Void, String>() {
    companion object {
        private const val TAG = "DataServerAsyncTask"
    }

    override fun doInBackground(vararg params: Void?): String {
        val serverSocket = ServerSocket(Constant.SocketType.PORT)
        try {
            Log.v(TAG, "Server ------> Socket opened")

            val client = serverSocket.accept()
            Log.v(TAG, "Server ------> Connection done")

            val input = client.getInputStream()
            val content = CommonUtil.readStream(input)
            input.close()

            return content
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        } finally {
            try {
                serverSocket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        workInf.handleWork(result)

        if (result != null) {
            Log.v(TAG, " ------> 这里是返回结果$result")
        }
    }
}