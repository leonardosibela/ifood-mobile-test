package br.com.sibela.tweetmood.retrofit.interceptor

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class TwitterAuthInterceptor(
    private val twitterConsumerKey: String,
    private val twitterConsumerSecret: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorization = Credentials.basic(twitterConsumerKey, twitterConsumerSecret)
        val modifiedRequest = request.newBuilder()
            .addHeader("Authorization", authorization)
            .addHeader("content-type", "application/json")
            .build()

        return chain.proceed(modifiedRequest)
    }
}