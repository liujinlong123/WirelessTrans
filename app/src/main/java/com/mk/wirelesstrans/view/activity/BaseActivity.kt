package com.mk.wirelesstrans.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mk.wirelesstrans.MyApplication
import com.mk.wirelesstrans.R
import com.mk.wirelesstrans.data.Constant

/**
 * @ClassName:      BaseActivity
 * @Description:    com.mk.wirelesstrans.view.activity
 * @Author:         Aiden.liu
 * @CreateDate:     2020/03/13 15:02
 */
abstract class BaseActivity : AppCompatActivity() {
    companion object {
        private const val MY_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accessibility()
    }

    open fun toast(id: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(MyApplication.instance, id, duration).show()
    }

    open fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(MyApplication.instance, content, duration).show()
    }

    /*--------------------------------------------------------------------------------------------*/
    /**
     * 需要申请权限:
     */
    private fun accessibility() {
        // 第 1 步: 检查是否有相应的权限
        val isAllGranted = checkPermissionAllGranted(Constant.Permission.ALL)

        // 如果要申请的权限全都拥有, 则直接执行代码
        if (isAllGranted) {
            doBackup()
            return
        }

        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(this, Constant.Permission.ALL,
            MY_PERMISSION_REQUEST_CODE
        )
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false
            }
        }
        return true
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            var isAllGranted = true
            // 判断是否所有的权限都已经授予了
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }
            if (isAllGranted) { // 如果所有的权限都授予了, 则执行备份代码
                doBackup()
            } else {
                // Permission Denied
                toast(R.string.PERMISSION_DENIED)
                finish()
            }
        }
    }

    /**
     * 第 4 步: 操作
     */
    private fun doBackup() {
    }
}