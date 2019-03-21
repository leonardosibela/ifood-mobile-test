package br.com.sibela.tweetmood.presenter

import br.com.sibela.tweetmood.model.OAuthBearerToken
import br.com.sibela.tweetmood.model.Tweet
import br.com.sibela.tweetmood.network.api.TwitterAPI
import br.com.sibela.tweetmood.network.api.TwitterAuthAPI
import br.com.sibela.tweetmood.task.UserSearchStask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSearchPresenter(val view: UserSearchStask.View) : UserSearchStask.Presenter {
    override fun getTwitterOAuthToken() {
        TwitterAuthAPI.getService().postCredentials().enqueue(object : Callback<OAuthBearerToken> {
            override fun onFailure(call: Call<OAuthBearerToken>, t: Throwable) {
                view.displayOAuthInternetErrorMessage()
            }

            override fun onResponse(call: Call<OAuthBearerToken>, response: Response<OAuthBearerToken>) {
                if (response.isSuccessful) {
                    val oAuthBearerToken = response.body()!!
                    view.saveOAuthToke(oAuthBearerToken.accessToken)
                    view.displayUserSearchForm()
                } else {
                    view.displayDefaultErrorMessage()
                }
            }
        })
    }

    override fun fetchUsersTwitter(accessToken: String, username: String) {
        TwitterAPI.getService(accessToken).statusesUserTimeline(username).enqueue(object : Callback<ArrayList<Tweet>> {
            override fun onFailure(call: Call<ArrayList<Tweet>>, t: Throwable) {
                view.displayInternetErrorMessage()
            }

            override fun onResponse(call: Call<ArrayList<Tweet>>, response: Response<ArrayList<Tweet>>) {
                if (response.isSuccessful) {
                    val tweets = response.body()!!
                    if (tweets.isEmpty()) {
                        view.displayUserHasNoTweetsMessage()
                    } else {
                        view.displayUserTweets(tweets)
                    }
                } else {
                    when(response.code()) {
                        401 -> view.displayUsersTweetsAreProtectedMessage(username)
                        404 -> view.userNotFound()
                    }
                }
            }
        })
    }
}