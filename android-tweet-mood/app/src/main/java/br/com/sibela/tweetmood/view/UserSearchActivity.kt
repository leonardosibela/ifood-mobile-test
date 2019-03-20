package br.com.sibela.tweetmood.view

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.extensions.readStringFromSharedPreferences
import br.com.sibela.tweetmood.extensions.writeOnSharedPreferences
import br.com.sibela.tweetmood.presenter.UserSearchPresenter
import br.com.sibela.tweetmood.task.UserSearchStask
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.CONSTRAINT_SET_INTERMEDIATE_DURATION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.CONSTRAINT_SET_STARTING_DELAY
import kotlinx.android.synthetic.main.activity_user_search_splash.*

class UserSearchActivity : AppCompatActivity(), UserSearchStask.View {

    private lateinit var presenter: UserSearchStask.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search_splash)
        presenter = UserSearchPresenter(this)

        val twitterAccessToken = readStringFromSharedPreferences(TWITTER_ACCESS_TOKEN)
        if (twitterAccessToken == null) {
            presenter.getTwitterOAuthToken()
        } else {
            Handler().postDelayed({
                displayUserSearchForm()
            }, CONSTRAINT_SET_STARTING_DELAY)
        }
    }

    override fun displayDefaultErrorMessage() {
        TODO("not implemented")
    }

    override fun displayUserSearchForm() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_user_search_form)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION)
        transition.duration = CONSTRAINT_SET_INTERMEDIATE_DURATION
        TransitionManager.beginDelayedTransition(container, transition)
        constraintSet.applyTo(container)
    }

    override fun saveOAuthToke(token: String) {
        writeOnSharedPreferences(TWITTER_ACCESS_TOKEN, token)
    }

    companion object {
        private const val TWITTER_ACCESS_TOKEN = "twitter_access_token"
    }
}