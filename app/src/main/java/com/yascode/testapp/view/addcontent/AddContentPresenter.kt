package com.yascode.testapp.view.addcontent

import android.content.Intent
import com.yascode.testapp.MyApp
import com.yascode.testapp.data.remote.ContentManager
import com.yascode.testapp.data.remote.Detail
import com.yascode.testapp.utils.AppSharedPreferences
import com.yascode.testapp.utils.Constant
import javax.inject.Inject

/**
 * Created by caksono21 on 30/11/17.
 */
class AddContentPresenter internal constructor(addContentView: AddContentContract.view) : AddContentContract.presenter {

    lateinit var AddContentView: AddContentContract.view

    @Inject lateinit var contentManager: ContentManager
    @Inject lateinit var sharedPref: AppSharedPreferences

    override suspend fun submitContent(file: String, detail: Detail): Boolean {
        return contentManager.postContent(sharedPref.getToken(Constant.KEY_TOKEN), file, detail)
    }

    init {
        MyApp.appComponent.inject(this)
        AddContentView = addContentView
    }
}