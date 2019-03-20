package br.com.sibela.tweetmood.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
class Tweet (val id: Long, val text: String, @SerializedName("created_at") val createdAt: String) : Parcelable {

    @IgnoredOnParcel
    var date : Date? = null
        get() = SimpleDateFormat(TWITTER_DATE_FORMAT, Locale.ENGLISH).parse(createdAt)

    companion object {
        private const val TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
    }
}