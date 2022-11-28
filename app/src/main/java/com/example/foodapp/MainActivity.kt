package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.viewModels.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.makeApiCall()


    }

    private fun observeRetrofitState(){
        lifecycleScope.launch{
            viewModel.retrofitState.collect{
                when(it){
                    MainViewModel.FoodRetrofitEvent.Idle -> TODO()
                    MainViewModel.FoodRetrofitEvent.Running -> TODO()
                    is MainViewModel.FoodRetrofitEvent.Successful -> {
                        if(it.response != null)
                        {
                            binding.apiResults = it.response
                        }
                    }
                    is MainViewModel.FoodRetrofitEvent.Failed -> TODO()
                }
            }
        }
    }
}