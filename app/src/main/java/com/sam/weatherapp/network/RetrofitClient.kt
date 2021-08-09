package com.sam.weatherapp.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object RetrofitClient {
    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val contentType = "application/json".toMediaType()
}