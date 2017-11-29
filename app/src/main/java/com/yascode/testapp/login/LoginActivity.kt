package com.yascode.testapp.login

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import com.yascode.testapp.R
import com.yascode.testapp.utils.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class LoginActivity : BaseActivity(), LoginContract.view {

    var progressDialog: ProgressDialog? = null
    lateinit var loginPresenter: LoginContract.presenter

    override fun hideLoader() {
        progressDialog?.dismiss()
    }

    override fun showLoader() {
        progressDialog = progressDialog ?: ProgressDialog(this)
        progressDialog?.setMessage("Logging In...")
        progressDialog?.setCancelable(false)

        progressDialog?.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this)
        
        sign_in_button.setOnClickListener { view ->

            job = launch(UI) {
                try {
                    val token = loginPresenter.postLogin(username.text.toString(), password.text.toString())

                } catch (e: Throwable) {
//                    Toast.makeText(LoginActivity.this, e.message, Toast.LENGTH_SHORT)
                    Log.e(LoginActivity::class.java.name, e.message)
                }
            }
        }
    }
}
