package com.mind.coolest.url_checker.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import com.mind.coolest.url_checker.utils.UrlAvailability
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_LOADING
import com.mind.coolest.url_checker.utils.isValidUrl
import com.mind.coolest.url_checker.utils.makeInputSequenceToCorrectUrl
import java.util.concurrent.Executors

/**
 * ClickHandler is provided as a handler bound to viewmodel, to interact with xml to catch button clicks
 */
class ClickHandler {

    fun onSortClick(sortPickerDialog: MutableLiveData<Boolean>) {
        sortPickerDialog.value = true
    }

    /**
     * After validating input field, adding to DB, and after that check and update the state
     */
    fun onEnterClick(viewModel: MainViewModel) {
        if (!viewModel.urlInput.value.isNullOrEmpty() && isValidUrl(viewModel.urlInput.value!!)) {

            val url = makeInputSequenceToCorrectUrl(viewModel.urlInput.value!!)
            val urlEntity = URLEntity(url = url, isAvailable = URL_VALIDATION_LOADING)
            viewModel.insert(urlEntity)
            UrlAvailability {
                viewModel.update(it)
            }.executeOnExecutor(Executors.newFixedThreadPool(10), urlEntity)
        }
    }

    /**
     * Refreshing Urls, checking availability
     */
    fun onRefreshClick(viewModel: MainViewModel) {
        val urlEntities = viewModel.dataUrlEntity.value

        if (!urlEntities.isNullOrEmpty()) {
            for (urlEntity in urlEntities) {
                UrlAvailability {
                    viewModel.update(it)
                }.executeOnExecutor(Executors.newFixedThreadPool(10), urlEntity)
            }
        }
    }
}