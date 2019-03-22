package br.com.sibela.tweetmood.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.INTERMEDIATE_ANIMATION_DURATION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.SENTIMENT_BACKGROUND_COLOR_DELAY
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.SENTIMENT_IMAGE_DELAY
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.SENTIMENT_MESSAGE_DELAY
import br.com.sibela.tweetmood.constants.ColorConstants.Companion.NO_INTERNET_BACKGROUND
import br.com.sibela.tweetmood.model.Sentiment
import br.com.sibela.tweetmood.presenter.SentimentAnalysisPresenter
import br.com.sibela.tweetmood.task.SentimentAnalysisTask
import kotlinx.android.synthetic.main.activity_sentiment_analysis_start.*

class SentimentAnalysisActivity : AppCompatActivity(), SentimentAnalysisTask.View {

    private lateinit var presenter: SentimentAnalysisTask.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentiment_analysis_start)
        presenter = SentimentAnalysisPresenter(this)

        val tweetText = intent.getStringExtra(TWEET_TEXT_DATA)
        presenter.analizeSentiment(tweetText)
    }

    override fun displaySentiment(sentiment: Sentiment) {
        hideSpinner()
        displaySentimentMessage(sentiment)
        displaySentimentImage(sentiment.image)
        changeBackgroundColor(sentiment.color.toInt())
    }

    override fun displayInternetErrorMessage() {
        hideSpinner()
        displaySentimentImage(R.drawable.ic_no_iternet)
        changeBackgroundColor(NO_INTERNET_BACKGROUND.toInt())
        displayMessage(getString(R.string.no_internet_connection_short_message))
    }

    private fun hideSpinner() {
        val fadeInAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out_animation)
        fadeInAnimation.duration = INTERMEDIATE_ANIMATION_DURATION
        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                loadingSpinner.visibility = View.INVISIBLE
            }
        })

        loadingSpinner.startAnimation(fadeInAnimation)
    }

    private fun displaySentimentMessage(sentiment: Sentiment) {
        val sentimentText = getString(sentiment.text).toUpperCase()
        val sentimentMessage = getString(R.string.sentiment_message, sentimentText)
        displayMessage(sentimentMessage)
    }

    private fun displayMessage(message: String) {
        Handler().postDelayed({
            sentimentMessage.text = message
            val fadeInAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_animation)
            fadeInAnimation.duration = INTERMEDIATE_ANIMATION_DURATION
            sentimentMessage.visibility = View.VISIBLE
            sentimentMessage.startAnimation(fadeInAnimation)
        }, SENTIMENT_MESSAGE_DELAY)
    }

    private fun displaySentimentImage(@DrawableRes image: Int) {
        imageView.setImageResource(image)

        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_sentiment_analysis_end)
        val transition = ChangeBounds()
        transition.interpolator =
                AnticipateOvershootInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION)
        transition.duration = INTERMEDIATE_ANIMATION_DURATION
        transition.startDelay = SENTIMENT_IMAGE_DELAY
        TransitionManager.beginDelayedTransition(container, transition)
        constraintSet.applyTo(container)
    }

    private fun changeBackgroundColor(@ColorInt color: Int) {
        val colorFade = ObjectAnimator.ofObject(
            container, "backgroundColor", ArgbEvaluator(),
            ContextCompat.getColor(this, android.R.color.background_light), color
        )
        colorFade.duration = INTERMEDIATE_ANIMATION_DURATION
        colorFade.startDelay = SENTIMENT_BACKGROUND_COLOR_DELAY
        colorFade.start()
    }

    companion object {
        const val TWEET_TEXT_DATA = "tweet_text_data"
    }
}