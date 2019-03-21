package br.com.sibela.tweetmood.view

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.AnimationUtils
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.adapter.TweetsAdapter
import br.com.sibela.tweetmood.constants.AnimationConstants
import br.com.sibela.tweetmood.model.Tweet
import kotlinx.android.synthetic.main.activity_tweets_list.*

class TweetsListActivity : AppCompatActivity(), TweetsAdapter.Callback {

    private lateinit var tweetsAdapter: TweetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val tweets: ArrayList<Tweet> = intent.getParcelableArrayListExtra(TWEETS_DATA_KEY)
        Handler().postDelayed({
            setupRecycler(tweets)
        }, AnimationConstants.DISPLAY_TWEETS_DELAY)
    }

    private fun setupRecycler(tweets: ArrayList<Tweet>) {
        tweetsRecycler.setHasFixedSize(true)
        tweetsRecycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        tweetsRecycler.layoutManager = LinearLayoutManager(tweetsRecycler.context)
        tweetsRecycler.addItemDecoration(CardSpacingItemDecoration(this, R.dimen.default_margin_between_elements))
        tweetsAdapter = TweetsAdapter(tweets, this)
        tweetsRecycler.adapter = tweetsAdapter
    }

    override fun displayFeeling(tweet: Tweet) {
        TODO("not implemented")
    }

    companion object {
        const val TWEETS_DATA_KEY = "tweets_data"
    }
}