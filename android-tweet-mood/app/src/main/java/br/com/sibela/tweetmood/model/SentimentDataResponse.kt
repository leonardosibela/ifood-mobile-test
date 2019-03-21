package br.com.sibela.tweetmood.model

class SentimentDataResponse(val documentSentiment: DocumentSentiment) {

    val sentiment: Sentiment
        get() {
            return when {
                documentSentiment.score > 0.33 -> Sentiment.HAPPY
                documentSentiment.score < -0.33 -> Sentiment.SAD
                else -> Sentiment.NEUTRAL
            }
        }

    class DocumentSentiment(val magnitude: Double, val score: Double)
}