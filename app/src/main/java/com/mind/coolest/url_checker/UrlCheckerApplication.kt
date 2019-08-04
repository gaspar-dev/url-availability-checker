package com.mind.coolest.url_checker

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mind.coolest.url_checker.data.local.db.DatabaseService
import com.mind.coolest.url_checker.data.local.db.dao.URLDao
import com.mind.coolest.url_checker.data.local.prefs.UrlSortPrefs

class UrlCheckerApplication : Application() {

    companion object {
        var instance: UrlCheckerApplication? = null
    }

    val urlDao: URLDao by lazy {
        Room.databaseBuilder(
            this, DatabaseService::class.java,
            "url-db"
        ).build().urlDao()
    }

    val urlSortPrefs: UrlSortPrefs by lazy {
        UrlSortPrefs(sharedPref)
    }

    private val sharedPref: SharedPreferences by lazy {
        getSharedPreferences("url-sort-prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}