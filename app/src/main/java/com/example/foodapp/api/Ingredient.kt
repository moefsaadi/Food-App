package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "description")
    val description: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "safety_level")
    val safetyLevel: String?
)