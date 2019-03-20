package br.com.sibela.tweetmood.extensions

import android.content.Context.MODE_PRIVATE
import android.support.v7.app.AppCompatActivity

private const val PREFERENCE_NAME: String = "twitter_mood"

fun AppCompatActivity.writeOnSharedPreferences(key: String, value: String) {
    val editor = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE).edit()
    editor.putString(key, value)
    editor.apply()
}

fun AppCompatActivity.readStringFromSharedPreferences(key: String): String? {
    val sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    return sharedPreferences.getString(key, null)
}