package com.quizsquiz.recipemaster.activities

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.quizsquiz.recipemaster.R
import com.quizsquiz.recipemaster.databinding.ActivityRecipeBinding
import com.quizsquiz.recipemaster.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_recipe.*
import java.io.File
import java.util.concurrent.ExecutionException

class RecipeActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding: ActivityRecipeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_recipe)
        setupActionBar()

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.tvRecipeTitle.setOnClickListener {
            saveImage()
        }
    }

    private fun saveImage() {
        val pictureSaveDialog = AlertDialog.Builder(this)
        pictureSaveDialog.setTitle("Select Action")
        pictureSaveDialog.setMessage(R.string.dialog_message)
        pictureSaveDialog.setPositiveButton(R.string.positive_yes) { _: DialogInterface, _: Int ->
            if (isWriteStorageAllowed()) {
                getImageFromApi()
            } else {
                Dexter.withContext(this)
                    .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            if (report!!.areAllPermissionsGranted()) {
                                getImageFromApi()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()
                        }
                    }).onSameThread()
                    .check()
            }
        }
        pictureSaveDialog.setNegativeButton(R.string.negative_no) { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        pictureSaveDialog.show()
    }

    fun getImageFromApi() {
        val exportDir = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera"
        try {
            Glide.with(this@RecipeActivity)
                .asBitmap()
                .load("http://mooduplabs.com/test/pizza2.jpg")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        File(
                            exportDir,
                            "recipe_pic_" + System.currentTimeMillis() / 10000 + ".jpg"
                        ).writeBitmap(resource, Bitmap.CompressFormat.JPEG, 90)
                    }
                })
        } catch (e: ExecutionException) {
            e.message?.let { Log.e("TAG", it) }
        } catch (e: InterruptedException) {
            e.message?.let { Log.e("TAG", it) }
        }
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_recipe_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
        }
        toolbar_recipe_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
            Toast.makeText(
                this@RecipeActivity,
                "File saved successfully: $absolutePath",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isWriteStorageAllowed(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }
}