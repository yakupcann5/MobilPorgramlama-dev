package com.mobilProgramlama.odev.common

sealed class RequestState<T>(val data : T? = null, val message : String? = null, val errorCode: Int? = null) {
    class Success<T>(data : T) : RequestState<T>(data)
    class Error<T>(message : String, data : T? = null, errorCode : Int? = null) : RequestState<T>(data, message, errorCode)
    class Loading<T>(data : T? = null) : RequestState<T>(data)
}
