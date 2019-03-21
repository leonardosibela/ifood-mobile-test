package br.com.sibela.tweetmood.model

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.constants.ColorConstants.Companion.SENTIMENT_HAPPY_YELLOW
import br.com.sibela.tweetmood.constants.ColorConstants.Companion.SENTIMENT_NEUTRAL_GRAY
import br.com.sibela.tweetmood.constants.ColorConstants.Companion.SENTIMENT_SAD_BLUE

enum class Sentiment(@StringRes val text: Int, @DrawableRes val image: Int, val color: Long) {

    HAPPY(R.string.sentiment_happy, R.drawable.ic_happy, SENTIMENT_HAPPY_YELLOW),
    SAD(R.string.sentiment_sad, R.drawable.ic_sad, SENTIMENT_SAD_BLUE),
    NEUTRAL(R.string.sentiment_neutral, R.drawable.ic_neutral, SENTIMENT_NEUTRAL_GRAY);
}