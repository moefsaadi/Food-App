package com.example.foodapp.api


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Nutrient(
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "percentOfDailyNeeds")
    val percentOfDailyNeeds: Double?,
    @Json(name = "unit")
    val unit: String?
)