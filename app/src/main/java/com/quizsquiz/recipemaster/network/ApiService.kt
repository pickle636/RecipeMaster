package com.quizsquiz.recipemaster.network

import com.quizsquiz.recipemaster.models.Recipe
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface ApiService {
    @GET("test/info.php")
    suspend fun getRecipe(): Recipe
    //fun getRecipe(): Deferred<Recipe>
}