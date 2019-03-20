package br.com.sibela.tweetmood.retrofit.api

import br.com.sibela.tweetmood.BuildConfig
import br.com.sibela.tweetmood.constants.TwitterAPIConstants.Companion.TWITTER_BASE_URL
import br.com.sibela.tweetmood.retrofit.interceptor.TwitterAuthInterceptor
import br.com.sibela.tweetmood.retrofit.service.TwitterAuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TwitterAuthAPI {

    private var twitterAuthService: TwitterAuthService? = null

    fun getService(): TwitterAuthService {
        if (twitterAuthService != null) {
            return twitterAuthService as TwitterAuthService
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(
            TwitterAuthInterceptor(
                BuildConfig.TWITTER_CONSUMER_KEY,
                BuildConfig.TWITTER_CONSUMER_SECRET_KEY
            )
        )

        val twitterClient = Retrofit.Builder()
            .baseUrl(TWITTER_BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        twitterAuthService = twitterClient.create(TwitterAuthService::class.java)
        return twitterAuthService as TwitterAuthService
    }
}