package com.mind.coolest.url_checker.data.repository

import androidx.lifecycle.LiveData
import com.mind.coolest.url_checker.data.local.db.dao.URLDao
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import com.mind.coolest.url_checker.data.local.prefs.UrlSortPrefs

class URLRepository(private val urlDao: URLDao, private val prefs: UrlSortPrefs) {

    fun getAllOrderByName(): LiveData<List<URLEntity>> = urlDao.getAllOrderByName()

    fun allOrderByAvailability(): LiveData<List<URLEntity>> = urlDao.getAllOrderByAvailability()

    fun getAllOrderByConnectionTime(): LiveData<List<URLEntity>> = urlDao.getAllOrderByConnectionTime()

    fun insert(urlEntity: URLEntity) = urlDao.insert(urlEntity)

    fun update(urlEntity: URLEntity) = urlDao.update(urlEntity)

    fun delete(urlEntity: URLEntity?) = urlDao.delete(urlEntity)

    fun getUrlSortedType() = prefs.getUrlSortedType()

    fun setUrlSortedType(sortType: String) = prefs.setUrlSortedType(sortType)
}