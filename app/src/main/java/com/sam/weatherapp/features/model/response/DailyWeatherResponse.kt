package com.sam.weatherapp.features.model.response

import com.sam.weatherapp.features.model.data_class.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherResponse(
    @SerialName("dt")
    val dateTime: Long = 0L,
    @SerialName("temp")
    val temperature: DailyTemperature = DailyTemperature(),
    @SerialName("weather")
    val weather: List<Weather> = listOf()
)