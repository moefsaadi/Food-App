package com.example.foodapp.app

import android.app.Application
import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "https://api.spoonacular.com/food/products/"

//URL for grocery product search : https://api.spoonacular.com/food/products/search
//URL for grocery product info : https://api.spoonacular.com/food/products/{id}

class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        lateinit var retrofit: Retrofit

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        super.onCreate()
    }
}