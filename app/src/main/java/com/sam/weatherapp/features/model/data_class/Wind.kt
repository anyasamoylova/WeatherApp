package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wind(
	@SerialName("speed")
	val speed: Double = 0.0
)
