package com.yascode.testapp.view.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.yascode.testapp.R
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.BaseActivity
import com.yascode.testapp.adapter.ContentAdapter
import com.yascode.testapp.view.addcontent.AddContentActivity
import com.yascode.testapp.view.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class MainActivity : BaseActivity(), MainContract.view, ContentAdapter.setOnItemClickListener {

    override fun onItemClick(id: Int) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    var progressDialog: ProgressDialog? = null
    private lateinit var mPresenter: MainContract.presenter
    private val uiContext: CoroutineContext = UI
    private val bgContext: CoroutineContext = CommonPool

    override fun showEmptyView() {
        empty_text.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        empty_text.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.menu_refresh -> consume { loadList() }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun loadList() {
        job = launch(uiContext) {
            showLoader()
            val task = async(bgContext) {
                mPresenter.loadList()
            }
            val contents = task.await()
            hideLoader()
            if (contents.size == 0) {
                showEmptyView()
            } else {
                hideEmptyView()
                refreshList(contents as ArrayList<Content>)
            }
        }
    }

    inline fun consume(f: () -> Unit): Boolean {
        loadList()
        return true
    }

    override fun showLoader() {
        progressDialog = progressDialog ?: ProgressDialog(this)
        progressDialog?.setMessage("Loading Data...")
        progressDialog?.setCancelable(false)

        progressDialog?.show()
    }

    override fun hideLoader() {
        progressDialog?.dismiss()
    }

    override fun refreshList(contents: ArrayList<Content>) {
        adapter = ContentAdapter(contents, this)
        rec_main.adapter = adapter
        rec_main.visibility = View.VISIBLE
    }

    private var adapter: ContentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter(this)
        rec_main.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        fab_add.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, AddContentActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadList()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }
}