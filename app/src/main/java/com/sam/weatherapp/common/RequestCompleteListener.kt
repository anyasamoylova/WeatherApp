package com.sam.weatherapp.common

interface RequestCompleteListener<T> {
    fun onRequestSuccess(data: T)
    fun onRequestFailed(errorMessage: String)
}