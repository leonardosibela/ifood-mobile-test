package br.com.sibela.tweetmood.task

import br.com.sibela.tweetmood.model.Tweet

interface UserSearchStask {

    interface View {
        fun displayDefaultErrorMessage()
        fun displayUserSearchForm()
        fun saveOAuthToke(token: String)
        fun displayUserTweets(tweets: ArrayList<Tweet>)
        fun displayInternetErrorMessage()
        fun displayUserHasNoTweetsMessage()
        fun displayUsersTweetsAreProtectedMessage(username: String)
        fun userNotFound()
        fun displayOAuthInternetErrorMessage()
    }

    interface Presenter {
        fun getTwitterOAuthToken()
        fun fetchUsersTwitter(accessToken: String, username: String)
    }
}