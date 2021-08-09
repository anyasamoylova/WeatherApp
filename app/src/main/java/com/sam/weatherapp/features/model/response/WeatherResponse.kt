package com.sam.weatherapp.features.model.response

import com.sam.weatherapp.features.model.data_class.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("coord")
	val coordinate: Coordinate = Coordinate(),
    @SerialName("weather")
	val weather: List<Weather> = listOf(),
    @SerialName("base")
	val base: String = "",
    @SerialName("main")
	val main: Main = Main(),
    @SerialName("visibility")
	val visibility: Int = 0,
    @SerialName("wind")
	val wind: Wind = Wind(),
    @SerialName("clouds")
	val clouds: Clouds = Clouds(),
    @SerialName("dt")
	val dateTime: Long = 0L,
    @SerialName("sys")
	val sys: Sys = Sys(),
    @SerialName("id")
	val id: Int = 0,
    @SerialName("name")
	val cityName: String = "",
    @SerialName("cod")
	val code: Int = 0
)