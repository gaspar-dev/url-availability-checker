package com.mind.coolest.url_checker.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import com.mind.coolest.url_checker.data.repository.URLRepository
import com.mind.coolest.url_checker.utils.MyExecutors
import com.mind.coolest.url_checker.utils.common.SORT_BY_AVAILABILITY
import com.mind.coolest.url_checker.utils.common.SORT_BY_CONNECTION_TIME

class MainViewModel(
    private val urlRepository: URLRepository,
    private val myExecutors: MyExecutors
) : ViewModel() {

    val urlInput: MutableLiveData<String> = MutableLiveData()
    val sortPickerDialog = MutableLiveData<Boolean>()
    var dataUrlEntity: LiveData<List<URLEntity>> = getNewtUrlEntityType(getUrlSortType())

    fun getUrlSortedPickerDialog(): LiveData<Boolean> = sortPickerDialog

    fun insert(urlEntity: URLEntity) {
        myExecutors.executorIO().execute {
            urlRepository.insert(urlEntity)
        }
    }

    fun update(urlEntity: URLEntity) {
        myExecutors.executorIO().execute {
            urlRepository.update(urlEntity)
        }
    }

    fun delete(position: Int) {
        myExecutors.executorIO().execute {
            urlRepository.delete(dataUrlEntity.value?.get(position))
        }
    }

    fun getUrlSortType() = urlRepository.getUrlSortedType()

    fun setUrlSortType(sortType: String) {
        urlRepository.setUrlSortedType(sortType)
        dataUrlEntity = getNewtUrlEntityType(sortType)
    }

    /**
     * Every change of sort type changing own observers
     */
    private fun getNewtUrlEntityType(sortType: String?): LiveData<List<URLEntity>> =
        when (sortType) {
            SORT_BY_CONNECTION_TIME -> urlRepository.getAllOrderByConnectionTime()
            SORT_BY_AVAILABILITY -> urlRepository.allOrderByAvailability()
            else -> urlRepository.getAllOrderByName()
        }
}

