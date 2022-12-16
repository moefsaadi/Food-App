package com.example.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.RecyclerViewAdapter
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

        //Initialize Recycler View in Grid Format (2 columns)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)


        //Set Search Activity on Nav Bar
        binding.bottomNavBar.selectedItemId = R.id.search_menu

        //Navigation actions when User clicks Bottom Navigation Bar
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


        //Search actions when User clicks 'Search' Button
        binding.searchBtn.setOnClickListener {
            val prodInput = binding.txtProdInput.text.toString()

            if(!binding.txtProdInput.toString().isEmpty()){
                val resultInput = binding.txtResultnumInput.text.toString().toInt()
                viewModel.makeFirstApiCall(prodInput,resultInput)
            }
            binding.searchBtn.visibility = View.INVISIBLE
            binding.txtProdInput.visibility = View.INVISIBLE
            binding.txtResultnumInput.visibility = View.INVISIBLE
            binding.txtProdLayout.visibility = View.INVISIBLE
            binding.txtResultnumLayout.visibility = View.INVISIBLE

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
                            binding.recyclerView.adapter = RecyclerViewAdapter(it.response.products)
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