package br.com.sibela.tweetmood.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

class Tweet (val id: Long, val text: String, @SerializedName("created_at") val createdAt: String) {

    var date : Date? = null
        get() = SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH).parse(createdAt)

    companion object {
        private const val TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    }
}