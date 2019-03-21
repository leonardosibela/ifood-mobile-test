package br.com.sibela.tweetmood.network.service

import br.com.sibela.tweetmood.constants.APIConstants.Companion.TWITTER_API_VERSION
import br.com.sibela.tweetmood.model.Tweet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TwitterService {

    @GET("/$TWITTER_API_VERSION/statuses/user_timeline.json")
    fun statusesUserTimeline(@Query("screen_name") screenName: String,
                             @Query("since_id") sinceId: Long? = null): Call<ArrayList<Tweet>>
}