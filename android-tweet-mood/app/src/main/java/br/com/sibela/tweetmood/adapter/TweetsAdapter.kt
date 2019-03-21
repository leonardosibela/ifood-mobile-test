package br.com.sibela.tweetmood.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.model.Tweet
import kotlinx.android.synthetic.main.tweet_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class TweetsAdapter(val tweets: ArrayList<Tweet>, private val callback: TweetsAdapter.Callback) :
    RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder>() {

    var allowClick = true

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TweetsViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.tweet_card, viewGroup, false)
        return TweetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.timestamp.text = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.ENGLISH).format(tweet.date).capitalize()
        holder.tweetContent.text = tweet.text
        holder.analyzeFeeling.setOnClickListener {
            if (allowClick) {
                allowClick = false
                callback.displayFeeling(tweet)
            }
        }
    }

    class TweetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tweetContent = itemView.tweetContent!!
        val timestamp = itemView.timestamp!!
        val analyzeFeeling = itemView.analyzeFeeling!!
    }

    interface Callback {
        fun displayFeeling(tweet: Tweet)
    }

    companion object {
        private const val DISPLAY_DATE_FORMAT = "MMM dd, yyyy - hh:mmaaa"
    }
}