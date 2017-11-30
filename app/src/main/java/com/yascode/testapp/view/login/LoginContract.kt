package com.yascode.testapp.view.login

/**
 * Created by caksono21 on 30/11/17.
 */
interface LoginContract {
    interface view {
        fun showLoader()
        fun hideLoader()

        fun gotoMain()
        fun checkState() : Boolean
    }

    interface presenter {
        suspend fun postLogin(username: String, password: String): String
    }
}