package com.yascode.testapp.view.detail

import com.yascode.testapp.data.local.Content

/**
 * Created by caksono21 on 30/11/17.
 */
interface DetailContract {
    interface view {
        fun showLoader()
        fun hideLoader()
        fun loadViewData(id: Int)
        fun setView(content: Content)
    }

    interface presenter {
        suspend fun getDetailContent(id: Int): Content
    }
}