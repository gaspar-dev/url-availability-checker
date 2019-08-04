package com.mind.coolest.url_checker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mind.coolest.url_checker.data.local.db.dao.URLDao
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        URLEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class DatabaseService : RoomDatabase() {
    abstract fun urlDao(): URLDao
}