package br.com.sibela.tweetmood.network.service

import br.com.sibela.tweetmood.constants.APIConstants.Companion.NATURAL_LANGUAGE_API_VERSION
import br.com.sibela.tweetmood.model.SentimentDataRequest
import br.com.sibela.tweetmood.model.SentimentDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface NaturalLanguageService {

    @POST("/$NATURAL_LANGUAGE_API_VERSION/documents:analyzeSentiment")
    fun analyzeSentiment(@Query("key") key: String, @Body body: SentimentDataRequest): Call<SentimentDataResponse>
}