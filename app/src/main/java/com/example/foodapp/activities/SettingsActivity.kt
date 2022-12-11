package com.example.foodapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set Settings Activity on Nav Bar
        binding.bottomNavBar.selectedItemId = R.id.setting_menu

        binding.bottomNavBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.setting_menu ->
                {
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
                    val intent = Intent(this,HistoryActivity::class.java)
                    overridePendingTransition(0,0)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}