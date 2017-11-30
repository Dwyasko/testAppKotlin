package com.yascode.testapp.utils

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by caksono21 on 30/11/17.
 */
class AppSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun putToken(key: String, token: String) {
        sharedPreferences.edit()
                .putString(key, token)
                .apply()
    }

    fun getToken(key: String): String {
        return sharedPreferences.getString(key, null)
    }

    fun putLoginState(key: String, isLoggedIn: Boolean) {
        sharedPreferences.edit()
                .putBoolean(key, isLoggedIn)
                .apply()
    }

    fun getIsLoggedIn(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}