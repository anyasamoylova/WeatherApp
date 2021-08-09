package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
	@SerialName("lon")
	val longitude: Double = 0.0,
	@SerialName("lat")
	val latitude: Double = 0.0
)
