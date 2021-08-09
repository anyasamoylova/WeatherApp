package com.sam.weatherapp.features.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseList (
    @SerialName("list")
    val citiesWithWeather: List<WeatherResponse> = listOf()
)