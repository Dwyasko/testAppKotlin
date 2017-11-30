package com.yascode.testapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.yascode.testapp.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by caksono21 on 29/11/17.
 */
@Module
class AppModule(val app: MyApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences = app.getSharedPreferences(
            "testapp_prefs", Context.MODE_PRIVATE
    )
}