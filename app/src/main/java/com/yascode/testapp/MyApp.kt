package com.yascode.testapp

import android.app.Application
import com.yascode.testapp.utils.di.AppComponent
import com.yascode.testapp.utils.di.AppModule
import com.yascode.testapp.utils.di.DaggerAppComponent

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