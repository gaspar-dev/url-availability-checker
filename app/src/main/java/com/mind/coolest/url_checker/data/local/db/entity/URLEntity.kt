package com.mind.coolest.url_checker.data.local.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mind.coolest.url_checker.utils.common.CONNECTION_TIMEOUT


@Entity(tableName = "url_checker_entity")
data class URLEntity(

    @PrimaryKey
    @ColumnInfo(name = "url")
    @NonNull
    var url: String = "",

    @ColumnInfo(name = "isAvailable")
    @NonNull
    var isAvailable: Int = 0,

    @ColumnInfo(name = "responseTime")
    @NonNull
    var responseTime: Int = CONNECTION_TIMEOUT
)