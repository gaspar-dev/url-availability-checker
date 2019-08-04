package com.mind.coolest.url_checker.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity

@Dao
interface URLDao {

    @Query("Select * FROM url_checker_entity order by url ASC")
    fun getAllOrderByName(): LiveData<List<URLEntity>>

    @Query("Select * FROM url_checker_entity order by isAvailable ASC")
    fun getAllOrderByAvailability(): LiveData<List<URLEntity>>

    @Query("Select * FROM url_checker_entity order by responseTime ASC")
    fun getAllOrderByConnectionTime(): LiveData<List<URLEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(urlEntity: URLEntity)

    @Update
    fun update(urlEntity: URLEntity)

    @Delete
    fun delete(urlEntity: URLEntity?)

}