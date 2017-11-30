package com.yascode.testapp.view.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.yascode.testapp.R
import com.yascode.testapp.BaseActivity
import com.yascode.testapp.MyApp
import com.yascode.testapp.utils.AppSharedPreferences
import com.yascode.testapp.utils.Constant
import com.yascode.testapp.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext

class LoginActivity : BaseActivity(), LoginContract.view {

    override fun gotoMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun checkState(): Boolean {
        return sharedPreferences.getIsLoggedIn(Constant.KEY_STATE)
    }

    var progressDialog: ProgressDialog? = null
    lateinit var loginPresenter: LoginContract.presenter

    private var uiContext: CoroutineContext = UI
    private var bgContext: CoroutineContext = CommonPool

    private val TAG: String = this::class.java.name

    @Inject lateinit var sharedPreferences: AppSharedPreferences

    init {
        MyApp.appComponent.inject(this)
    }

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

        if (checkState()) gotoMain()

        sign_in_button.setOnClickListener { view ->
            job = launch(uiContext) {
                try {
                    showLoader()

                    Log.e(TAG, "--> Username - " + username.text.toString())

                    val task = async(bgContext) {
                        loginPresenter.postLogin(username.text.toString(), password.text.toString())
                    }

                    val token = task.await()
                    sharedPreferences.putToken(Constant.KEY_TOKEN, token)
                    sharedPreferences.putLoginState(Constant.KEY_STATE, true)
                    hideLoader()
                    gotoMain()
                    Log.d(TAG, token)
                } catch (e: Throwable) {
                    Log.e(LoginActivity::class.java.name, e.message)
                }
            }
        }
    }
}
