package com.haxon.githubreposapp.util

sealed class GeneralResponse<T>(val data: T? = null, message: String? = null) {
    class Success<T>(data: T?) : GeneralResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : GeneralResponse<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true): GeneralResponse<T>(null)
}