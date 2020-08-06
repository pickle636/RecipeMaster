package com.quizsquiz.recipemaster.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.recipemaster.R
import com.quizsquiz.recipemaster.databinding.ActivityRecipeBinding
import com.quizsquiz.recipemaster.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_recipe.*

class RecipeActivity : BaseActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding: ActivityRecipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe)
        setupActionBar()

        binding.lifecycleOwner = this
        binding.viewmodel = MainActivity().viewModel
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_recipe_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            actionBar.title = resources.getString(R.string.recipe_title)
        }
        toolbar_recipe_activity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}