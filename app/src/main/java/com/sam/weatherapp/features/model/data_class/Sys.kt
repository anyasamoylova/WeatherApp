package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
	@SerialName("country")
	val country: String = "",
	@SerialName("id")
	val id: Int = 0,
	@SerialName("type")
	val type: Int = 0,
	@SerialName("message")
	val message: Double = 0.0,
	@SerialName("sunrise")
	val sunrise: Int = 0,
	@SerialName("sunset")
	val sunset: Int = 0
)
