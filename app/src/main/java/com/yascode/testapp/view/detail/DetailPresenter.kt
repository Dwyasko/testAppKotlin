package com.yascode.testapp.view.detail

import com.yascode.testapp.MyApp
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.data.remote.ContentManager
import com.yascode.testapp.utils.AppSharedPreferences
import com.yascode.testapp.utils.Constant
import javax.inject.Inject

/**
 * Created by caksono21 on 30/11/17.
 */
class DetailPresenter internal constructor(val view: DetailContract.view) : DetailContract.presenter {

    @Inject lateinit var contentManager: ContentManager
    @Inject lateinit var sharedPref: AppSharedPreferences

    lateinit var detailView: DetailContract.view

    init {
        MyApp.appComponent.inject(this)
        this.detailView = view
    }

    suspend override fun getDetailContent(id: Int): Content {
        return contentManager.getContent(id, sharedPref.getToken(Constant.KEY_TOKEN))
    }

}