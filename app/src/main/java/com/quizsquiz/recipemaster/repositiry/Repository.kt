package com.quizsquiz.recipemaster.repositiry

import androidx.lifecycle.LiveData
import com.quizsquiz.recipemaster.models.Recipe
import com.quizsquiz.recipemaster.network.RetrofitBuilder
import kotlinx.coroutines.*

object Repository {

    var job: CompletableJob? = null

    fun getRecipe(): LiveData<Recipe> {
        job = Job()
        return object: LiveData<Recipe>(){
            override fun onActive() {
                super.onActive()
                job?.let{ theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        val recipe = RetrofitBuilder.apiService.getRecipe()
                        withContext(Dispatchers.Main){
                            value = recipe
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJobs(){
        job?.cancel()
    }

}