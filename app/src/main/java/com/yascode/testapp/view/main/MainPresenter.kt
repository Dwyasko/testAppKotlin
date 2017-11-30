package com.yascode.testapp.view.main

import android.content.Context
import android.widget.ImageView
import com.yascode.testapp.MyApp
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.data.remote.ContentManager
import com.yascode.testapp.utils.AppSharedPreferences
import com.yascode.testapp.utils.Constant
import javax.inject.Inject

/**
 * Created by caksono21 on 29/11/17.
 */
class MainPresenter internal constructor(context: Context) : MainContract.presenter {

    var mView: MainContract.view
    @Inject lateinit var contentManager: ContentManager
    @Inject lateinit var sharedPreferences: AppSharedPreferences

    init {
        MyApp.appComponent.inject(this)
        mView = context as MainContract.view
    }

    override suspend fun loadList(): List<Content> {
        val result = contentManager.getListImage(sharedPreferences.getToken(Constant.KEY_TOKEN))
        return result
    }
}