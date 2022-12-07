package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Servings(
    @Json(name = "number")
    val number: Int?,
    @Json(name = "size")
    val size: Int?,
    @Json(name = "unit")
    val unit: String?
)