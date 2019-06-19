package com.example.androidsample.data.local

import android.content.Context
import android.preference.PreferenceManager

class SharedPrefRepoImpl(context: Context) : SharedPrefRepo {

    companion object {
        private const val LAST_DATABASE_FETCH = "LAST_DATABASE_FETCH"
        private const val USER_SESSION = "USER_SESSION"
    }

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    override var lastDataSetFetch: Long
        get() = prefs.getLong(LAST_DATABASE_FETCH, 0)
        set(value) = prefs.edit().putLong(LAST_DATABASE_FETCH, value).apply()

    override var userSession: String
        get() = prefs.getString(USER_SESSION, "") ?: ""
        set(value) = prefs.edit().putString(USER_SESSION, value).apply()

}
