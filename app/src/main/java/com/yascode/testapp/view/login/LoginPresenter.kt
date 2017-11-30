package com.yascode.testapp.view.login

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
        val result = contentManager.postLogin(username, password)
        return result;
    }

    init {
        MyApp.appComponent.inject(this)
        loginView = context as LoginContract.view;
    }

}