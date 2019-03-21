package br.com.sibela.tweetmood.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.model.Sentiment
import kotlinx.android.synthetic.main.activity_sentiment_analysis_start.*

class SentimentAnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentiment_analysis_start)

        val tweetText = intent.getStringExtra(TWEET_TEXT_DATA)

        Handler().postDelayed({
            displaySentiment(Sentiment.HAPPY)
        }, 100)
    }

    fun displaySentiment(sentiment: Sentiment) {
        displaySentimentMessage(sentiment)
        displaySentimentImage(sentiment.image)
        changeBackgroundColor(sentiment.color.toInt())
    }

    private fun displaySentimentMessage(sentiment: Sentiment) {
        Handler().postDelayed({
            val sentimentText = getString(sentiment.text).toUpperCase()
            sentimentMessage.text = getString(R.string.sentiment_message, sentimentText)

            val fadeInAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_animation)
            sentimentMessage.visibility = View.VISIBLE
            sentimentMessage.startAnimation(fadeInAnimation)
        }, 2000)
    }

    private fun displaySentimentImage(@DrawableRes image: Int) {
        imageView.setImageResource(image)

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_sentiment_analysis_end)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1000
        transition.startDelay = 2000
        TransitionManager.beginDelayedTransition(container, transition)
        constraintSet.applyTo(container)
    }

    private fun changeBackgroundColor(@ColorInt color: Int) {
        val colorFade = ObjectAnimator.ofObject(
            container, "backgroundColor", ArgbEvaluator(),
            ContextCompat.getColor(this, android.R.color.background_light), color
        )
        colorFade.duration = 3500
        colorFade.startDelay = 500
        colorFade.start()
    }

    companion object {
        const val TWEET_TEXT_DATA = "tweet_text_data"
    }
}