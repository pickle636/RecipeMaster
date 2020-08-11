package com.quizsquiz.recipemaster.network

import com.quizsquiz.recipemaster.models.Recipe
import retrofit2.http.GET


interface ApiService {
    @GET("test/info.php")
    suspend fun getRecipe(): Recipe
}