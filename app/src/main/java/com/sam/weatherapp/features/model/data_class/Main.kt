package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
	@SerialName("temp")
	val temperature: Double = 0.0,
	@SerialName("temp_min")
	val temperatureMin: Double = 0.0,
	@SerialName("temp_max")
	val temperatureMax: Double = 0.0,
	@SerialName("humidity")
	val humidity: Int = 0,
	@SerialName("pressure")
	val pressure: Int = 0,
	@SerialName("feels_like")
	val feelsLike: Double = 0.0
)
