package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nutrition(
    @Json(name = "caloricBreakdown")
    val caloricBreakdown: CaloricBreakdown?,
    @Json(name = "calories")
    val calories: Double?,
    @Json(name = "carbs")
    val carbs: String?,
    @Json(name = "fat")
    val fat: String?,
    @Json(name = "nutrients")
    val nutrients: List<Nutrient>?,
    @Json(name = "protein")
    val protein: String?
)