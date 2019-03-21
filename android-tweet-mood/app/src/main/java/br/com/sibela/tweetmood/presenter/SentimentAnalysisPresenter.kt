package br.com.sibela.tweetmood.presenter

import br.com.sibela.tweetmood.BuildConfig
import br.com.sibela.tweetmood.model.SentimentDataRequest
import br.com.sibela.tweetmood.model.SentimentDataResponse
import br.com.sibela.tweetmood.network.api.NaturalLanguageAPI
import br.com.sibela.tweetmood.task.SentimentAnalysisTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SentimentAnalysisPresenter(val view: SentimentAnalysisTask.View) : SentimentAnalysisTask.Presenter {

    override fun analizeSentiment(text: String) {
        NaturalLanguageAPI.getService().analyzeSentiment(
            BuildConfig.NATURAL_LANGUAGE_API_KEY,
            SentimentDataRequest(SentimentDataRequest.Document(text))).enqueue(object : Callback<SentimentDataResponse> {
            override fun onFailure(call: Call<SentimentDataResponse>, t: Throwable) {
                TODO("not implemented")
            }

            override fun onResponse(call: Call<SentimentDataResponse>, response: Response<SentimentDataResponse>) {
                if (response.isSuccessful) {
                    view.displaySentiment(response.body()!!.sentiment)
                }
            }
        })
    }
}