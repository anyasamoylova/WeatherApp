package com.sam.weatherapp.features.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeekWeatherResponse (
    @SerialName("lat")
    val latitude: Double = 0.0,
    @SerialName("lon")
    val longitude: Double = 0.0,
    @SerialName("hourly")
    val hourlyForecast: List<HourlyWeatherResponse> = listOf(),
    @SerialName("daily")
    val dailyForecast: List<DailyWeatherResponse> = listOf(),
)