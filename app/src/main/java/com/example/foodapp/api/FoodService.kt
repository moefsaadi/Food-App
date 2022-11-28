package com.example.foodapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FoodService {

    @Headers("x-api-key: be6e8758bd23476da8ae4d867def3aa7")
    @GET("search?query={food}&number={numResults}")
    suspend fun getFood(
        @Path("food") food: String,
        @Path("numResults") numResults: Int,
    ): Call<FoodResponse>

    @Headers("x-api-key: be6e8758bd23476da8ae4d867def3aa7")
    @GET("{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): Call<ProductResponse>


}