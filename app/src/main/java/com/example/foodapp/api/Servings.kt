package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Servings(
    @Json(name = "number")
    val number: Double,
    @Json(name = "size")
    val size: Double,
    @Json(name = "unit")
    val unit: String
)