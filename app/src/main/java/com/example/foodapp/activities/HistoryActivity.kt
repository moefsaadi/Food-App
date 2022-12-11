package com.example.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityHistoryBinding


class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set History Activity on Nav Bar
        binding.bottomNavBar.selectedItemId = R.id.history_menu

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
                    val intent = Intent(this,MainActivity::class.java)
                    overridePendingTransition(0,0)
                    startActivity(intent)
                    true
                }
                R.id.history_menu ->
                {
                    true
                }
                else -> false
            }
        }
    }
}