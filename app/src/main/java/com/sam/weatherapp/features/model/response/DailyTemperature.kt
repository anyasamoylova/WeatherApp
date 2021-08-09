package com.sam.weatherapp.features.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyTemperature (
    @SerialName("day")
    val dayTemperature: Double = 0.0,
    @SerialName("night")
    val nightTemperature: Double = 0.0,
)