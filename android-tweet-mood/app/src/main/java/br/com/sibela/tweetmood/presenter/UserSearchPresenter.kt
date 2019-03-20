package br.com.sibela.tweetmood.presenter

import br.com.sibela.tweetmood.model.OAuthBearerToken
import br.com.sibela.tweetmood.retrofit.api.TwitterAuthAPI
import br.com.sibela.tweetmood.task.UserSearchStask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSearchPresenter(val view: UserSearchStask.View) : UserSearchStask.Presenter {

    override fun getTwitterOAuthToken() {
        TwitterAuthAPI.getService().postCredentials().enqueue(object : Callback<OAuthBearerToken> {
            override fun onFailure(call: Call<OAuthBearerToken>, t: Throwable) {
                view.displayDefaultErrorMessage()
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
}