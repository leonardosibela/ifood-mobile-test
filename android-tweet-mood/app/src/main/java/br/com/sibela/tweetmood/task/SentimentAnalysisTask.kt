package br.com.sibela.tweetmood.task

import br.com.sibela.tweetmood.model.Sentiment

interface SentimentAnalysisTask {
    interface View {
        fun displaySentiment(sentiment: Sentiment)
        fun displayInternetErrorMessage()
    }

    interface Presenter {
        fun analizeSentiment(text: String)
    }
}