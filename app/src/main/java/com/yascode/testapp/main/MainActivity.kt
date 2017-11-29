package com.yascode.testapp.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.yascode.testapp.R
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.utils.ContentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.view {

    private lateinit var mPresenter: MainContract.presenter

    override fun showLoader() {
    }

    override fun hideLoader() {
    }

    override fun refreshList(contents: ArrayList<Content>) {
        adapter?.setContents(contents)
    }

    private var adapter: ContentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter(this)
        rec_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        mPresenter.loadList()
    }
}
