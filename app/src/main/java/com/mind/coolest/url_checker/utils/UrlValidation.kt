package com.mind.coolest.url_checker.utils

import android.os.AsyncTask
import android.util.Patterns
import com.mind.coolest.url_checker.data.local.db.entity.URLEntity
import com.mind.coolest.url_checker.utils.common.CONNECTION_TIMEOUT
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_AVAILABLE
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_LOADING
import com.mind.coolest.url_checker.utils.common.URL_VALIDATION_UNAVAILABLE
import java.net.HttpURLConnection
import java.net.URL

class UrlAvailability(private val resultListener: (result: URLEntity) -> Unit) :
    AsyncTask<URLEntity, Void, URLEntity>() {

    override fun doInBackground(vararg params: URLEntity): URLEntity {
        val startTime = System.currentTimeMillis()
        val urlEntity = params[0]
        urlEntity.isAvailable = URL_VALIDATION_LOADING
        return try {
            HttpURLConnection.setFollowRedirects(false)
            val con = URL(urlEntity.url).openConnection() as HttpURLConnection
            con.connectTimeout = CONNECTION_TIMEOUT
            con.readTimeout = CONNECTION_TIMEOUT
            con.requestMethod = "HEAD"

            if (con.responseCode == HttpURLConnection.HTTP_OK) {
                val time = System.currentTimeMillis() - startTime
                urlEntity.isAvailable = URL_VALIDATION_AVAILABLE
                urlEntity.responseTime = time.toInt()
            } else {
                urlEntity.isAvailable = URL_VALIDATION_UNAVAILABLE
            }
            urlEntity
        } catch (e: Exception) {
            e.printStackTrace()
            urlEntity.isAvailable = URL_VALIDATION_UNAVAILABLE
            urlEntity
        }
    }

    override fun onPostExecute(result: URLEntity) {
        super.onPostExecute(result)
        resultListener.invoke(result)
    }
}

fun isValidUrl(url: String): Boolean {
    val p = Patterns.WEB_URL
    val m = p.matcher(url.toLowerCase())
    return m.matches()
}

fun makeInputSequenceToCorrectUrl(url: String): String {
    return if (url.startsWith("http://www.") || url.startsWith("https://www.")) {
        url
    } else if (url.startsWith("www.")) {
        val newUrl = "https://$url"
        newUrl
    } else if (url.startsWith("https://")) {
        val newUrl = "https://www.${url.substring(url.indexOf("/") + 2)}"
        newUrl
    }
    else if (url.startsWith("http://")) {
        val newUrl = "http://www.${url.substring(url.indexOf("/") + 2)}"
        newUrl
    }
    else {
        return "https://www.$url"
    }
}

