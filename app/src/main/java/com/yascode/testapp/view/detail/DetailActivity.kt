package com.yascode.testapp.view.detail

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yascode.testapp.BaseActivity
import com.yascode.testapp.R
import com.yascode.testapp.data.local.Content
import com.yascode.testapp.utils.loadImg
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class DetailActivity : BaseActivity(), DetailContract.view {
    override fun setView(content: Content) {
        txt_summary.text = content.summary
        txt_detail.text = content.detail
        img_content.loadImg(content.original_url)
    }

    var progressDialog: ProgressDialog? = null
    private val uiContext: CoroutineContext = UI
    private val bgContext: CoroutineContext = CommonPool

    override fun loadViewData(id: Int) {
        job = launch(uiContext) {
            showLoader()
            val task = async(bgContext) {
                detailPresenter.getDetailContent(id)
            }

            val content = task.await()
            if (content != null) setView(content)

            hideLoader()
        }
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

    private lateinit var detailPresenter: DetailContract.presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailPresenter = DetailPresenter(this)
        val id = intent.getIntExtra("id", 0)

        if (id != 0) loadViewData(id)
    }
}
