package com.quizsquiz.recipemaster.activities

import android.app.Dialog
import android.content.SharedPreferences
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quizsquiz.recipemaster.R
import kotlinx.android.synthetic.main.dialog_progress.*

open class BaseActivity: AppCompatActivity() {
    lateinit var mSharedPreferences: SharedPreferences
    private var doubleBackToExitPressedOnce = false
    private lateinit var mProcessDialog: Dialog


    fun showProgressDialog(text: String) {
        mProcessDialog = Dialog(this)
        mProcessDialog.setContentView(R.layout.dialog_progress)
        mProcessDialog.tv_progress_text.text = text
        mProcessDialog.show()
    }

    fun hideProgressDialog() {
        mProcessDialog.dismiss()
    }
    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,
            getString(R.string.please_click_back_again_to_exit, resources.getString(R.string.app_name)), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            this.doubleBackToExitPressedOnce = false
        }, 1500)
    }
}

