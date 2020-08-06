package com.quizsquiz.recipemaster.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.recipemaster.R
import com.quizsquiz.recipemaster.databinding.ActivityMainBinding
import com.quizsquiz.recipemaster.utils.Constants
import com.quizsquiz.recipemaster.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        setSupportActionBar(toolbar_main_activity)
        fab_get_recipe.setOnClickListener {
            val intent = Intent(this, RecipeActivity::class.java)
            startActivity(intent)
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


    }


    override fun onBackPressed() {
        doubleBackToExit()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}