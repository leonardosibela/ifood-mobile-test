package br.com.sibela.tweetmood.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.SearchView
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.AVARAGE_ACTIVITY_TRANSITION_TIME
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.CONSTRAINT_SET_INTERMEDIATE_DURATION
import br.com.sibela.tweetmood.constants.AnimationConstants.Companion.CONSTRAINT_SET_STARTING_DELAY
import br.com.sibela.tweetmood.extensions.*
import br.com.sibela.tweetmood.model.Tweet
import br.com.sibela.tweetmood.presenter.UserSearchPresenter
import br.com.sibela.tweetmood.task.UserSearchStask
import kotlinx.android.synthetic.main.activity_user_search_splash.*
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class UserSearchActivity : AppCompatActivity(), UserSearchStask.View {

    private lateinit var presenter: UserSearchStask.Presenter
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_user_search_splash)
        presenter = UserSearchPresenter(this)

        retrieveAccessTokenAndDisplayForm()
        setupListeners()
    }

    private fun retrieveAccessTokenAndDisplayForm() {
        val twitterAccessToken = readStringFromSharedPreferences(TWITTER_ACCESS_TOKEN)
        if (twitterAccessToken == null) {
            presenter.getTwitterOAuthToken()
        } else {
            Handler().postDelayed({
                displayUserSearchForm()
            }, CONSTRAINT_SET_STARTING_DELAY)
        }
    }

    override fun displayInternetErrorMessage() {
        stopLoading()
        displayNoInternetSnack()

    }

    override fun displayOAuthInternetErrorMessage() {
        displayNoInternetSnack { retrieveAccessTokenAndDisplayForm() }
    }

    override fun displayUserHasNoTweetsMessage() {
        stopLoading()
        errorMessage.setText(R.string.search_user_user_does_not_have_tweets)
        errorMessage.visibility = View.VISIBLE
    }

    override fun displayUsersTweetsAreProtectedMessage(username: String) {
        stopLoading()
        errorMessage.text = getString(R.string.search_user_users_tweets_are_protected, username)
        errorMessage.visibility = View.VISIBLE
    }

    override fun userNotFound() {
        stopLoading()
        errorMessage.setText(R.string.search_user_user_not_found)
        errorMessage.visibility = View.VISIBLE
    }

    override fun displayDefaultErrorMessage() {
        stopLoading()
        displaySnackbar(message = R.string.unexpected_error_message, duration = Snackbar.LENGTH_LONG)
    }

    override fun displayUserSearchForm() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.activity_user_search_form)
        val transition = ChangeBounds()
        transition.interpolator =
                AnticipateOvershootInterpolator(ANTICIPATE_OVERSHOOT_INTERPOLATOR_INTERMEDIATE_TENSION)
        transition.duration = CONSTRAINT_SET_INTERMEDIATE_DURATION
        TransitionManager.beginDelayedTransition(container, transition)
        constraintSet.applyTo(container)
    }

    override fun saveOAuthToke(token: String) {
        writeOnSharedPreferences(TWITTER_ACCESS_TOKEN, token)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener { onSearchUserClicked() }
        userSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                errorMessage.visibility = View.INVISIBLE
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()
                fetchUsersTwitter(query)
                return true
            }
        })
    }

    private fun getAccessToken() = readStringFromSharedPreferences(TWITTER_ACCESS_TOKEN)

    private fun onSearchUserClicked() {
        fetchUsersTwitter(userSearchView.query.toString())
    }

    private fun fetchUsersTwitter(username: String) {
        this.username = username
        loadingSpinner.visibility = View.VISIBLE
        loginButton.visibility = View.INVISIBLE
        presenter.fetchUsersTwitter(getAccessToken()!!, username)
    }

    override fun displayUserTweets(tweets: ArrayList<Tweet>) {
        val intent = Intent(this, TweetsListActivity::class.java)
        intent.putParcelableArrayListExtra(TweetsListActivity.TWEETS_DATA_KEY, tweets)
        intent.putExtra(TweetsListActivity.USERNAME_DATA_KEY, username)
        startActivity(intent)
        Handler().postDelayed({
            stopLoading()
        }, AVARAGE_ACTIVITY_TRANSITION_TIME)
    }

    private fun stopLoading() {
        loadingSpinner.visibility = View.INVISIBLE
        loginButton.visibility = View.VISIBLE
    }

    companion object {
        private const val TWITTER_ACCESS_TOKEN = "twitter_access_token"
    }
}