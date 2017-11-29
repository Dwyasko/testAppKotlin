package com.yascode.testapp.utils.di

import com.yascode.testapp.detail.DetailActivity
import com.yascode.testapp.login.LoginActivity
import com.yascode.testapp.main.MainActivity
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
}