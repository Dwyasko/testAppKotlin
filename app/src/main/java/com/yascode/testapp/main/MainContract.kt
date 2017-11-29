package com.yascode.testapp.main

import com.yascode.testapp.data.local.Content

/**
 * Created by caksono21 on 29/11/17.
 */
interface MainContract {

    interface view {

        fun showLoader()
        fun hideLoader()
        fun refreshList(contents: ArrayList<Content>)

    }

    interface presenter {

        suspend fun loadList(): List<Content>
    }
}