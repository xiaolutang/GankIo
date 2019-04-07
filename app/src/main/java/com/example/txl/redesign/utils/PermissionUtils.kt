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
    val splashPermission = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)

}
fun main(args: Array<String>) {
    println("PermissionUtils  "+permissionUtils.splashPermission)
}