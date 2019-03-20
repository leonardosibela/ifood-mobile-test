package br.com.sibela.tweetmood.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.model.Tweet

class TweetsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweets_list)

        val tweets: ArrayList<Tweet> = intent.getParcelableArrayListExtra(TWEETS_DATA_KEY)
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TWEETS_DATA_KEY = "tweets_data"
    }
}