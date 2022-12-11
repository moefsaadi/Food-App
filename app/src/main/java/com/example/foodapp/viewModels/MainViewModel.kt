package com.example.foodapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.api.ApiResult
import com.example.foodapp.api.FoodResponse
import com.example.foodapp.api.FoodService
import com.example.foodapp.app.App.Companion.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.create

class MainViewModel: ViewModel() {

    sealed class FoodRetrofitEvent {
        object Idle : FoodRetrofitEvent()
        object Running : FoodRetrofitEvent()
        data class Successful(val response: FoodResponse?) : FoodRetrofitEvent()
        data class Failed(val msg: String?) : FoodRetrofitEvent()
    }

    private val _retrofitState = MutableStateFlow<FoodRetrofitEvent>(FoodRetrofitEvent.Idle)
    val retrofitState: StateFlow<FoodRetrofitEvent> = _retrofitState



    fun makeFirstApiCall(prodInput: String, resultInput: Int ){
        viewModelScope.launch(Dispatchers.IO) {

            val foodService = retrofit.create(FoodService::class.java)
            val result = foodService.getFood(prodInput,resultInput)


            when(result){
                //every other response code, 401, 403, etc.
                is ApiResult.Failure -> {
                    _retrofitState.tryEmit(FoodRetrofitEvent.Failed("${result.responseCode}"))
                }
                //any other error, like internet
                ApiResult.NetworkFailure -> {
                    _retrofitState.tryEmit(FoodRetrofitEvent.Failed("Network Failure!"))
                }
                //only reach when 200-OK
                is ApiResult.Success -> {
                    _retrofitState.tryEmit(FoodRetrofitEvent.Successful(result.result))

                    //make the second call with this
                    //val firstProductId = result.result.products[0].id
                }
            }
        }
    }

    fun makeSecondApiCall(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val foodService = retrofit.create(FoodService::class.java)

            val result = foodService.getProduct(id)
            when (result) {
                is ApiResult.Failure -> {
                    Log.i("SECONDCALL", "failed with ${result.responseCode}")
                }
                ApiResult.NetworkFailure -> {
                    Log.i("SECONDCALL", "failed unknowingly")
                }
                is ApiResult.Success -> {
                    Log.i("SECONDCALL", "pepsi costs this much \$${result.result.id}")
                }
            }
        }
    }
}