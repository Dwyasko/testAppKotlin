package com.yascode.testapp


import android.support.v4.app.DialogFragment
import kotlinx.coroutines.experimental.Job

/**
 * Created by caksono21 on 30/11/17.
 */
open class BaseDialogFragment : DialogFragment() {
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