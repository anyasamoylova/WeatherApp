package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
	@SerialName("icon")
	val icon: String = "",
	@SerialName("description")
	val description: String = "",
	@SerialName("main")
	val main: String = "",
	@SerialName("id")
	val id: Int = 0
)
