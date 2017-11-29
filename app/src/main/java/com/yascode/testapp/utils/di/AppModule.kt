package com.yascode.testapp.utils.di

import android.app.Application
import android.content.Context
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

}