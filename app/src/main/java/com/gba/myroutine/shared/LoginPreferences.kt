package com.gba.myroutine.shared

import android.content.Context
import android.content.SharedPreferences

class LoginPreferences(context: Context) {
    private val mPreferences: SharedPreferences =
            context.getSharedPreferences("taskShared", Context.MODE_PRIVATE)

    fun store(key: String, value: String) {
        mPreferences.edit().putString(key, value).apply()
    }

    fun storeInt(key: String, value: Int) {
        mPreferences.edit().putInt(key, value).apply()
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }

    fun get(key: String): String {
        return mPreferences.getString(key, "") ?: ""
    }
}