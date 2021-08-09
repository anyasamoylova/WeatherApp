package com.sam.weatherapp.features.model.data_class

data class WeatherDataModel (
    val coordinates: String = "",
    var dateTime: String = "",
    var temperature: String = "",
    var cityAndCountry: String = "",
    var weatherConditionIconUrl: String = "",
    var weatherConditionIconDescription: String = "",
    var humidity: String = "",
    var pressure: String = "",
    var visibility: String = "",
    var sunrise: String = "",
    var sunset: String = "",
    var windSpeed: String = "",
    var description: String = ""
)