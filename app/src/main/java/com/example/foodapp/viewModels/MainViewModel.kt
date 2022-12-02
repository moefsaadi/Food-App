package com.example.foodapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.FoodResponse
import com.example.foodapp.api.FoodService
import com.example.foodapp.api.ProductResponse
import com.example.foodapp.app.App.Companion.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainViewModel: ViewModel() {



    sealed class FoodRetrofitEvent {
        object Idle : FoodRetrofitEvent()
        object Running : FoodRetrofitEvent()
        data class Successful(val response: FoodResponse?) : FoodRetrofitEvent()
        data class Failed(val msg: String?) : FoodRetrofitEvent()
    }

    private val _retrofitState = MutableStateFlow<FoodRetrofitEvent>(FoodRetrofitEvent.Idle)
    val retrofitState: StateFlow<FoodRetrofitEvent> = _retrofitState



    fun makeApiCall(){
        viewModelScope.launch(Dispatchers.IO) {

            val foodService = retrofit.create(FoodService::class.java)
            val foodCall = foodService.getFood("pepsi",2)

            foodCall.enqueue(object: Callback<FoodResponse> {
                override fun onResponse(
                    call: Call<FoodResponse>,
                    response: Response<FoodResponse>
                ) {
                    if(!response.isSuccessful){
                        val codeStr = response.code().toString()
                        _retrofitState.tryEmit(FoodRetrofitEvent.Failed(codeStr))
                        return
                    }
                    _retrofitState.tryEmit(FoodRetrofitEvent.Successful(response.body()))
                }

                override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                    _retrofitState.tryEmit(FoodRetrofitEvent.Failed(t.message))
                }
            })

        }
    }
}