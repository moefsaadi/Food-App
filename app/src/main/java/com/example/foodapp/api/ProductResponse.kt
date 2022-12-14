package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "breadcrumbs")
    val breadcrumbs: List<String>,
    @Json(name = "imageType")
    val imageType: String,
    @Json(name = "badges")
    val badges: List<String>,
    @Json(name = "importantBadges")
    val importantBadges: List<String>,
    @Json(name = "ingredientCount")
    val ingredientCount: Int,
    @Json(name = "generatedText")
    val generatedText: String?,
    @Json(name = "ingredientList")
    val ingredientList: String,
    @Json(name = "ingredients")
    val ingredients: List<Ingredient>,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "aisle")
    val aisle: String?,
    @Json(name = "nutrition")
    val nutrition: Nutrition,
    @Json(name = "price")
    val price: Double,
    @Json(name = "servings")
    val servings: Servings,
    @Json(name = "spoonacularScore")
    val spoonacularScore: Double?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "image")
    val image: String?,

)