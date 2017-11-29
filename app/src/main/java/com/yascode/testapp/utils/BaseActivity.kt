package com.yascode.testapp.utils

import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.experimental.Job

/**
 * Created by caksono21 on 30/11/17.
 */
open class BaseActivity : AppCompatActivity() {
    protected var job: Job? = null

    override fun onResume() {
        super.onResume()
        job = null
    }

    override fun onPause() {
        super.onPause()
        job?.cancel()
        job = null
    }
}