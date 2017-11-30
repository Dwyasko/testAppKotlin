package com.yascode.testapp.di

import com.yascode.testapp.view.addcontent.AddContentPresenter
import com.yascode.testapp.view.detail.DetailActivity
import com.yascode.testapp.view.detail.DetailPresenter
import com.yascode.testapp.view.login.LoginActivity
import com.yascode.testapp.view.login.LoginPresenter
import com.yascode.testapp.view.main.MainActivity
import com.yascode.testapp.view.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by caksono21 on 30/11/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ContentModule::class))
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(detailActivity: DetailActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(presenter: LoginPresenter)
    fun inject(presenter: MainPresenter)
    fun inject(presenter: AddContentPresenter)
    fun inject(presenter: DetailPresenter)
}