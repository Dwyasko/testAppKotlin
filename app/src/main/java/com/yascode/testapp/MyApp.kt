package com.yascode.testapp

import android.app.Application
import com.yascode.testapp.di.AppComponent
import com.yascode.testapp.di.AppModule
import com.yascode.testapp.di.DaggerAppComponent

/**
 * Created by caksono21 on 29/11/17.
 */
class MyApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}