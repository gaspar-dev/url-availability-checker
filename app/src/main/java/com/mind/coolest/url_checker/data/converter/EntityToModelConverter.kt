package com.mind.coolest.url_checker.data.converter

import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import com.mind.coolest.url_checker.ui.main.model.URLItemModel
import com.mind.coolest.url_checker.utils.URLState
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_AVAILABLE
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_LOADING

fun urlEntityToUrlItemViewModel(urlEntities: List<URLEntity>): List<URLItemModel> {

    val data = arrayListOf<URLItemModel>()

    for (urlEntity in urlEntities) {
        data.add(urlEntityToUrlItemViewModel(urlEntity))
    }
    return data
}

private fun urlEntityToUrlItemViewModel(urlEntity: URLEntity): URLItemModel {
    val urlItemViewModel = URLItemModel()
    urlItemViewModel.urlName.set(urlEntity.url)
    val state = when(urlEntity.isAvailable) {
        URL_VALIDATION_LOADING -> URLState.LOADING
        URL_VALIDATION_AVAILABLE -> URLState.AVAILABLE
        else -> URLState.UNAVAILABLE
    }
    urlItemViewModel.urlState.set(state)
    return urlItemViewModel
}
