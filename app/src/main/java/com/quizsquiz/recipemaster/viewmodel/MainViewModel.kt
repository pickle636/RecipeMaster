package com.quizsquiz.recipemaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.quizsquiz.recipemaster.models.Recipe
import com.quizsquiz.recipemaster.repositiry.Repository

class MainViewModel : ViewModel() {

    val recipe: LiveData<Recipe> = Repository.getRecipe()

    fun cancelJobs() {
        Repository.cancelJobs()
    }
}

