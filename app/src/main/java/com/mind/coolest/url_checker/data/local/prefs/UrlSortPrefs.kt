package com.mind.coolest.url_checker.data.local.prefs

import android.content.SharedPreferences

class UrlSortPrefs(private val prefs: SharedPreferences) {
    companion object {
        const val KEY_URL_SORT = "PREF_KEY_URL_SORT"
    }

    fun getUrlSortedType(): String? =
        prefs.getString(KEY_URL_SORT, null)

    fun setUrlSortedType(sortType: String) =
        prefs.edit().putString(KEY_URL_SORT, sortType).apply()
}