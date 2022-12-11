package com.example.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.viewModels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set Search Activity on Nav Bar
        binding.bottomNavBar.selectedItemId = R.id.search_menu

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.setting_menu ->
                {
                    val intent = Intent(this,SettingsActivity::class.java)
                    overridePendingTransition(0,0)
                    startActivity(intent)
                    true
                }
                R.id.search_menu ->
                {
                    true
                }
                R.id.history_menu ->
                {
                    val intent = Intent(this,HistoryActivity::class.java)
                    overridePendingTransition(0,0)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        binding.searchBtn.setOnClickListener {
            val prodInput = binding.txtProdInput.text.toString()

            if(!binding.txtProdInput.toString().isEmpty()){
                val resultInput = binding.txtResultnumInput.text.toString().toInt()
                viewModel.makeFirstApiCall(prodInput,resultInput)
            }
            //viewModel.makeSecondApiCall(765467)
            observeRetrofitState()
        }



    }

    private fun observeRetrofitState(){
        lifecycleScope.launch{
            viewModel.retrofitState.collect{
                when(it){
                    MainViewModel.FoodRetrofitEvent.Idle -> {}
                    MainViewModel.FoodRetrofitEvent.Running -> {}
                    is MainViewModel.FoodRetrofitEvent.Successful -> {
                        if(it.response != null)
                        {

                        }
                    }
                    is MainViewModel.FoodRetrofitEvent.Failed -> {
                        val text = "Failure: ${it.msg}"
                        Toast.makeText(applicationContext,text,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}