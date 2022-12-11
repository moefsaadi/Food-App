package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(
    @Json(name = "products")
    val products: List<Product>,
    @Json(name = "totalProducts")
    val totalProducts: Int,
    @Json(name = "type")
    val type: String,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "number")
    val number: Int,
)