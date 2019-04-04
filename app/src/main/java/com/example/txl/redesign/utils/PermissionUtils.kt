package com.example.txl.redesign.utils

import android.Manifest

/**
 *@author TXL
 *description :权限工具
 */

val permissionUtils = PermissionUtils()

class PermissionUtils{
    /**
     * 启动页所需要的权限
     * */
    val splashPermission = arrayOf({ Manifest.permission_group.LOCATION;Manifest.permission_group.STORAGE})

    fun main(args: Array<String>) {
        println("splashPermission")
    }
}