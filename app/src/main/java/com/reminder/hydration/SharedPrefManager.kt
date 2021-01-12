package com.reminder.hydration

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Hydration", Context.MODE_PRIVATE)


    fun <T> save(key: String, value: T) {
        when (value) {
            is String -> {
                sharedPreferences.edit()
                    .putString(key, value)
                    .apply()
            }
            is Int -> {
                sharedPreferences.edit()
                    .putInt(key, value)
                    .apply()
            }
            is Long -> {
                sharedPreferences.edit()
                    .putLong(key, value)
                    .apply()
            }
            is Boolean -> {
                sharedPreferences.edit()
                    .putBoolean(key, value)
                    .apply()
            }
        }
    }

    fun load(key: String, default: Int) = sharedPreferences.getInt(key, default)


    companion object {
        private var sharedPrefManagerInstance: SharedPrefManager? = null;
        fun getInstance(context: Context): SharedPrefManager {
            if (sharedPrefManagerInstance == null) {
                synchronized(this) {
                    if (sharedPrefManagerInstance == null) {
                        sharedPrefManagerInstance = SharedPrefManager(context)
                    }
                }
            }
            return sharedPrefManagerInstance!!;
        }
    }
}