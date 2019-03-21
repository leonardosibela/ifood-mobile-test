package br.com.sibela.tweetmood.model

class SentimentDataRequest(val document: Document, val encodingType: String = "UTF8") {

    class Document(val content: String, val type: String = "PLAIN_TEXT")
}
