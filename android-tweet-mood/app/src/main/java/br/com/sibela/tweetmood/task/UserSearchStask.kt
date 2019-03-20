package br.com.sibela.tweetmood.task

interface UserSearchStask {

    interface View {
        fun dysplayDefaultErrorMessage()
        fun dysplayUserSearchForm()
    }

    interface Presenter {
        fun getTwitterOAuthToken()
    }
}