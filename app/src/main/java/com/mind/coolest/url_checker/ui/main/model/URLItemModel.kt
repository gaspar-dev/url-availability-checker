package com.mind.coolest.url_checker.ui.main.model

import androidx.databinding.ObservableField
import com.mind.coolest.url_checker.utils.URLState

class URLItemModel {
    val urlName: ObservableField<String> = ObservableField()
    val urlState: ObservableField<URLState> = ObservableField(URLState.AVAILABLE)
}