package br.com.sibela.tweetmood.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class TwitterInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val modifiedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("content-type", "application/json")
            .build()

        return chain.proceed(modifiedRequest)
    }
}