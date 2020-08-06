package com.quizsquiz.recipemaster.models


data class Recipe(
    val description: String,
    val imgs: List<String>,
    val ingredients: List<String>,
    val preparing: List<String>,
    val title: String
)