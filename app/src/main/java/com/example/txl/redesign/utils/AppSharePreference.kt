package com.example.txl.redesign.utils

import android.content.Context
import android.text.TextUtils

/**
 *@author TXL
 *description :
 */
class AppSharePreference(){
    fun <E> saveData(key: String, value: Map<String, E>?, context: Context?) {
        if (TextUtils.isEmpty(key) || value == null || value.isEmpty() || context == null)
            return
        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val entrySet = value.entries
        val iterator = entrySet.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (!TextUtils.isEmpty(entry.key)) {
                when {
                    entry.value is String -> editor.putString(entry.key, entry.value as String)
                    entry.value is Int -> editor.putInt(entry.key, entry.value as Int)
                    entry.value is Float -> editor.putFloat(entry.key, entry.value as Float)
                    entry.value is Boolean -> editor.putBoolean(entry.key, entry.value as Boolean)
                    entry.value is Long -> editor.putLong(entry.key, entry.value as Long)
                }
            }
        }
        editor.apply()
    }

    /**
     * 取存取的数据Map
     * @param key
     * @param context
     * @return
     */
    fun getSharedData(key: String, context: Context?): Map<String, *>? {
        return if (TextUtils.isEmpty(key) || context == null) null else context.getSharedPreferences(key, Context.MODE_PRIVATE).all
    }

    /**
     * 清除
     * @param key
     * @param context
     */
    fun clearShareData(key: String, context: Context?) {
        if (TextUtils.isEmpty(key) || context == null)
            return
        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}