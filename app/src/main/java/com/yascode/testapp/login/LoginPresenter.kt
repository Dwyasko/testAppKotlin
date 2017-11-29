package com.yascode.testapp.login

import android.content.Context
import com.yascode.testapp.MyApp
import com.yascode.testapp.data.remote.ContentManager
import javax.inject.Inject

/**
 * Created by caksono21 on 30/11/17.
 */
class LoginPresenter(context: Context) : LoginContract.presenter {

    @Inject lateinit var contentManager: ContentManager
    var loginView: LoginContract.view

    override suspend fun postLogin(username: String, password: String): String {
        loginView.showLoader()
        val result = contentManager.postLogin(username, password)

        loginView.hideLoader()
        return result;
    }

    init {
        MyApp.appComponent.inject(context as LoginActivity)
        loginView = context;
    }

}