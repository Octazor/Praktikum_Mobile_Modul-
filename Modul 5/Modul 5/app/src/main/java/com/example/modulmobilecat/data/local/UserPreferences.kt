package com.example.modulmobilecat.data.local

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("cat_preferences", Context.MODE_PRIVATE)
    private val _darkMode = MutableStateFlow(sharedPreferences.getBoolean(KEY_DARK_MODE, false))

    val darkMode: StateFlow<Boolean> = _darkMode

    fun setDarkMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        _darkMode.value = enabled
    }

    private companion object {
        const val KEY_DARK_MODE = "dark_mode"
    }
}
