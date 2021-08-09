package com.sam.weatherapp.ui

import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.features.model.data_class.Coordinate
import com.sam.weatherapp.features.model.data_class.WeatherDataModel

interface DayForecastView {
    fun handleProgressBarVisibility(visibility: Int)
    fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel, coordinate: Coordinate)
    fun onWeatherInfoFetchFailure(errorMessage: String)
}