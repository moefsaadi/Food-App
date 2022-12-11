package com.example.foodapp.api

/**
 * A representation of a network call result
 */
sealed class ApiResult<out T> {

    /**
     * A successful network call result
     */
    data class Success<T>(
        val result: T,
        val responseCode: Int
    ) : ApiResult<T>()

    /**
     * A failed network call result
     */
    data class Failure<T>(
        val responseCode: Int?,
        val throwable: Throwable?
    ) : ApiResult<T>()

    object NetworkFailure : ApiResult<Nothing>() {

        override fun toString(): String {
            return "Network failure"
        }
    }

}