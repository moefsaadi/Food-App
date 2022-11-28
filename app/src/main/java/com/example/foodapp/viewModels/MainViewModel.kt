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

class MainViewModel: ViewModel() {

    sealed class FoodRetrofitEvent {
        object Idle : FoodRetrofitEvent()
        object Running : FoodRetrofitEvent()
        data class Successful(val response: FoodResponse?) : FoodRetrofitEvent()
        data class Failed(val msg: String?) : FoodRetrofitEvent()
    }

    private val _retrofitState = MutableStateFlow<FoodRetrofitEvent>(FoodRetrofitEvent.Idle)
    val retrofitState: StateFlow<FoodRetrofitEvent> = _retrofitState

    sealed class ProductRetrofitEvent {
        object Idle : ProductRetrofitEvent()
        object Running : ProductRetrofitEvent()
        data class Successful(val response: ProductResponse?) : ProductRetrofitEvent()
        data class Failed(val msg: String?) : ProductRetrofitEvent()
    }

    private val _prodretrofitState = MutableStateFlow<ProductRetrofitEvent>(ProductRetrofitEvent.Idle)
    val prodretrofitState: StateFlow<ProductRetrofitEvent> = _prodretrofitState

    fun makeApiCall(){
        viewModelScope.launch(Dispatchers.IO) {

            val foodService = retrofit.create(FoodService::class.java)
            val foodCall = foodService.getFood("pepsi",1)
            val productCall = foodService.getProduct(2018165)

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

            productCall.enqueue(object: Callback<ProductResponse>{
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (!response.isSuccessful) {
                        val codeStr = response.code().toString()
                        _prodretrofitState.tryEmit(ProductRetrofitEvent.Failed(codeStr))
                        return
                    }
                    _prodretrofitState.tryEmit(ProductRetrofitEvent.Successful(response.body()))
                }
                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                    _prodretrofitState.tryEmit(ProductRetrofitEvent.Failed(t.message))
                }
            })
        }
    }
}