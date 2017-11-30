package com.yascode.testapp.view.addcontent

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.yascode.testapp.BaseActivity
import com.yascode.testapp.R
import com.yascode.testapp.data.remote.Detail
import com.yascode.testapp.utils.Constant
import com.yascode.testapp.utils.Constant.CAMERA_REQUEST_CODE
import com.yascode.testapp.utils.Constant.GALLERY_REQUEST_CODE
import com.yascode.testapp.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_image.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.experimental.CoroutineContext
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager

class AddContentActivity : BaseActivity(), AddContentContract.view {
    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun gotoMain() {
        Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@AddContentActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun hideLoader() {
        progressDialog?.dismiss()
    }

    var progressDialog: ProgressDialog? = null

    override fun showLoader() {
        progressDialog = progressDialog ?: ProgressDialog(this)
        progressDialog?.setMessage("Processing...")
        progressDialog?.setCancelable(false)

        progressDialog?.show()
    }

    lateinit var addConterPresenter: AddContentPresenter
    var imageFilePath: String = ""
    private var uiContext: CoroutineContext = UI
    private var bgContext: CoroutineContext = CommonPool

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_image)

        addConterPresenter = AddContentPresenter(this)
        btn_submit_content.setOnClickListener { view ->
            if (!imageFilePath.isEmpty()) {
                val detail = Detail(et_sum.text.toString(), et_desc.text.toString())
                job = launch(uiContext) {
                    showLoader()
                    val task = async(bgContext) {
                        addConterPresenter.submitContent(imageFilePath, detail)
                    }
                    val isSuccess = task.await()

                    hideLoader()
                    if (isSuccess) gotoMain() else showError("Error Occured")
                }
            } else {
                showError("Please input photo first!")
            }
        }

        img_content.setOnClickListener { view ->

            val items = arrayOf<CharSequence>("Camera", "Gallery", "Cancel")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Add Photo!")
            builder.setItems(items) { dialog, item ->
                if (items[item] == "Camera") {
                    cameraIntent()
                } else if (items[item] == "Gallery") {
                    galleryIntent()
                } else if (items[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }
    }

    fun cameraIntent() {
        val imageFile = createImageFile()
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val authorities = packageName + ".fileprovider"
            val imageUri = FileProvider.getUriForFile(this, authorities, imageFile)

            val resInfoList = packageManager.queryIntentActivities(cameraIntent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }
    }

    fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {

                    job = launch(uiContext) {
                        showLoader()

                        val bitmap = async(bgContext) {
                            setScaledBitmap()
                        }

                        val bmp = bitmap.await()

                        Log.d("check H", bmp.height.toString())
                        Log.d("check W", bmp.width.toString())
                        img_content.setImageBitmap(bmp)

                        hideLoader()
                    }
                } else {
                    imageFilePath = ""
                }
            }
        /*Constant.GALLERY_REQUEST_CODE -> {
            if (resultCode == Activity.RESULT_OK) {
                addConterPresenter.crop(data?.)
            }
        }*/
        }
    }

    suspend fun setScaledBitmap(): Bitmap {

        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFilePath, bmOptions)

        val bitmapWidth = bmOptions.outWidth
        val bitmapHeight = bmOptions.outHeight
        val maxSize = 500

        val scaleFactor = Math.min(bitmapWidth / maxSize, bitmapHeight / maxSize)

        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor

        val bitmap = BitmapFactory.decodeFile(imageFilePath, bmOptions)

        val bmp = Bitmap.createScaledBitmap(bitmap, maxSize, maxSize, false)

        val file = File(imageFilePath)

        val fileOs = FileOutputStream(file)

        val bao = ByteArrayOutputStream()

        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao)

        fileOs.write(bao.toByteArray())
        fileOs.flush()
        fileOs.close()

        return bmp
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir.exists()) storageDir.mkdirs()
        val imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        imageFilePath = imageFile.absolutePath
        return imageFile
    }
}
