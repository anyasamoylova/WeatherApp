package com.sam.weatherapp.features.presenter

interface WeatherInfoPresenter {
//    fun detachView()
    fun fetchWeatherInfoByCityId(cityId: Int)
    fun fetchWeatherInfoByCoordinates(latitude: Double, longitude: Double)
    fun fetchWeekWeatherInfoByCoordinates(latitude: Double, longitude: Double)
}