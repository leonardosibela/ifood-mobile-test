package br.com.sibela.tweetmood.retrofit.api

import br.com.sibela.tweetmood.constants.TwitterAPIConstants.Companion.TWITTER_BASE_URL
import br.com.sibela.tweetmood.retrofit.interceptor.TwitterInterceptor
import br.com.sibela.tweetmood.retrofit.service.TwitterService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TwitterAPI {

    private var twitterService: TwitterService? = null

    fun getService(twitterAccessToken: String): TwitterService {
        if (twitterService != null) {
            return twitterService as TwitterService
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(TwitterInterceptor(twitterAccessToken))

        val twitterClient = Retrofit.Builder()
            .baseUrl(TWITTER_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        twitterService = twitterClient.create(TwitterService::class.java)
        return twitterService as TwitterService
    }
}