package br.com.sibela.tweetmood.retrofit.service

import br.com.sibela.tweetmood.model.OAuthBearerToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TwitterAuthService {

    @FormUrlEncoded
    @POST("oauth2/token")
    fun postCredentials(@Field("grant_type") grantType: String = "client_credentials"): Call<OAuthBearerToken>

}