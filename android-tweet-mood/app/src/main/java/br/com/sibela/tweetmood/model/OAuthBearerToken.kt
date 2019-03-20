package br.com.sibela.tweetmood.model

import com.google.gson.annotations.SerializedName

class OAuthBearerToken (
    @SerializedName("token_type") val tokenType: String = "bearer",
    @SerializedName("access_token") val accessToken: String)