package com.yascode.testapp.view.addcontent

import com.yascode.testapp.data.remote.Detail

/**
 * Created by caksono21 on 30/11/17.
 */
interface AddContentContract {
    interface view {
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun gotoMain()
    }

    interface presenter {
        suspend fun submitContent(file: String, detail: Detail): Boolean

//        fun crop()
    }
}