package com.yascode.testapp.main

import android.content.Context
import android.widget.ImageView
import com.yascode.testapp.MyApp
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.data.remote.ContentManager

/**
 * Created by caksono21 on 29/11/17.
 */
class MainPresenter internal constructor(context: Context) : MainContract.presenter {

    var mView: MainContract.view
    lateinit var contentManager: ContentManager

    init {
        MyApp.appComponent.inject(context as MainActivity)
        mView = context
    }

    override suspend fun loadList(): List<Content> {
        mView.showLoader()

        val result = contentManager.getListImage()

        mView.hideLoader()
        return result
    }

    fun ImageView.loadUrl() {

    }

}