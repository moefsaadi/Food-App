package com.example.foodapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityDetailedFoodBinding

class DetailedFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}