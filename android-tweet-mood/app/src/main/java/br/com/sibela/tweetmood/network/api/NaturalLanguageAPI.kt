package br.com.sibela.tweetmood.network.api

import br.com.sibela.tweetmood.constants.APIConstants
import br.com.sibela.tweetmood.network.service.NaturalLanguageService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NaturalLanguageAPI {

    private var naturalLanguageService: NaturalLanguageService? = null

    fun getService(): NaturalLanguageService {
        if (naturalLanguageService != null) {
            return naturalLanguageService as NaturalLanguageService
        }

        val naturalLanguageClient = Retrofit.Builder()
            .baseUrl(APIConstants.NATURAL_LANGUAGE_BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        naturalLanguageService = naturalLanguageClient.create(NaturalLanguageService::class.java)
        return naturalLanguageService as NaturalLanguageService
    }
}