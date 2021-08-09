package com.sam.weatherapp.features.model.data_class

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
	@SerialName("all")
	val all: Int = 0
)
