package com.example.foodapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodService {

    @Headers("x-api-key: be6e8758bd23476da8ae4d867def3aa7")
    @GET("products/search")
    suspend fun getFood(
        @Query("query") food: String,
        @Query("number") numResults: Int,
    ): ApiResult<FoodResponse>

    @Headers("x-api-key: be6e8758bd23476da8ae4d867def3aa7")
    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): ApiResult<ProductResponse>

}



//@GET("search?")

//@Query("food") food: String,
//@Query("numResults") numResults: Int

//"https://api.spoonacular.com/food/products/search?food=pepsi&numResults=1"