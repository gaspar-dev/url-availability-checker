package com.mind.coolest.url_checker.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mind.coolest.url_checker.R
import com.mind.coolest.url_checker.utils.URLState

object BindingAdapter {

    @BindingAdapter("app:urlState")
    @JvmStatic
    fun changeItemIcon(image: ImageView, urlState: URLState) {
        when (urlState) {
            URLState.LOADING -> image.setBackgroundResource(R.drawable.ic_loading)
            URLState.AVAILABLE -> image.setBackgroundResource(R.drawable.ic_available)
            URLState.UNAVAILABLE -> image.setBackgroundResource(R.drawable.ic_unavailable)
        }
    }
}