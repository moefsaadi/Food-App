package com.example.foodapp.api

import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * An adapter factory that transforms a suspended [Response] to a standard [ApiResult]
 *
 * @see https://github.com/yujinyan/retrofit-suspend-result-adapter/blob/master/src/main/kotlin/me/yujinyan/retrofit/SuspendResultCallAdapterFactory.kt
 */
class SuspendApiResultCallAdapterFactory : CallAdapter.Factory() {

    private var hasConverterForApiResult: Boolean? = null
    private fun Retrofit.hasConverterForApiResultType(resultType: Type): Boolean {
        // If converter exists for any `ApiResult<T>`,
        // user registered custom converter for `ApiResult` type.
        // No need to check again.
        return if (hasConverterForApiResult == true) true else runCatching {
            nextResponseBodyConverter<ApiResult<*>>(
                null, resultType, arrayOf()
            )
        }.isSuccess.also { hasConverterForApiResult = it }
    }

    /**
     * Represents Type `Call<T>`, where `T` is passed in [dataType]
     */
    private class CallDataType(
        private val dataType: Type
    ) : ParameterizedType {
        override fun getActualTypeArguments(): Array<Type> = arrayOf(dataType)
        override fun getRawType(): Type = Call::class.java
        override fun getOwnerType(): Type? = null
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // suspend function is represented by `Call<ApiResult<T>>`
        if (getRawType(returnType) != Call::class.java) return null
        if (returnType !is ParameterizedType) return null

        // ApiResult<T>
        val resultType: Type = getParameterUpperBound(0, returnType)
        if (getRawType(resultType) != ApiResult::class.java
            || resultType !is ParameterizedType
        ) return null

        val dataType = getParameterUpperBound(0, resultType)

        val delegateType = if (retrofit.hasConverterForApiResultType(resultType))
            returnType else CallDataType(dataType)

        val delegate: CallAdapter<*, *> = retrofit
            .nextCallAdapter(this, delegateType, annotations)

        return CatchingCallAdapter(delegate)
    }

    private class CatchingCallAdapter(
        private val delegate: CallAdapter<*, *>,
    ) : CallAdapter<Any, Call<ApiResult<*>>> {
        override fun responseType(): Type = delegate.responseType()
        override fun adapt(call: Call<Any>): Call<ApiResult<*>> = CatchingCall(call)
    }

    private class CatchingCall(
        private val delegate: Call<Any>,
    ) : Call<ApiResult<*>> {

        override fun enqueue(callback: Callback<ApiResult<*>>) = delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    callback.onResponse(this@CatchingCall, Response.success(ApiResult.Success(body, response.code())))
                } else {
                    val throwable = HttpException(response)
                //    Timber.w(throwable)
                    callback.onResponse(
                        this@CatchingCall,
                        Response.success(ApiResult.Failure<Any>(response.code(), throwable))
                    )
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
           //     Timber.w(t)
                callback.onResponse(
                    this@CatchingCall,
                    Response.success(ApiResult.NetworkFailure)
                )
            }
        })

        override fun clone(): Call<ApiResult<*>> = CatchingCall(delegate)
        override fun execute(): Response<ApiResult<*>> =
            throw UnsupportedOperationException("Suspend function should not be blocking.")

        override fun isExecuted(): Boolean = delegate.isExecuted
        override fun cancel(): Unit = delegate.cancel()
        override fun isCanceled(): Boolean = delegate.isCanceled
        override fun request(): Request = delegate.request()
        override fun timeout(): Timeout = delegate.timeout()
    }
}