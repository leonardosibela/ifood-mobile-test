package br.com.sibela.tweetmood.task

interface UserSearchStask {

    interface View {
        fun displayDefaultErrorMessage()
        fun displayUserSearchForm()
        fun saveOAuthToke(token: String)
    }

    interface Presenter {
        fun getTwitterOAuthToken()
    }
}