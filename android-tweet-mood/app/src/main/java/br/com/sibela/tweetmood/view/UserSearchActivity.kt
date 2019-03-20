package br.com.sibela.tweetmood.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.sibela.tweetmood.R
import br.com.sibela.tweetmood.task.UserSearchStask

class UserSearchActivity : AppCompatActivity(), UserSearchStask.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_search_splash)
    }

    override fun dysplayDefaultErrorMessage() {
        TODO("not implemented")
    }

    override fun dysplayUserSearchForm() {
        TODO("not implemented")
    }
}