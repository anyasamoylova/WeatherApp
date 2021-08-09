package com.sam.weatherapp.features.model.data_class

data class WeekWeatherDataModel (
        val date: String = "",
        val hourlyWeather: MutableList<WeatherDataModel> = mutableListOf(),
        val dailyWeather: MutableList<WeatherDataModel> = mutableListOf()
)