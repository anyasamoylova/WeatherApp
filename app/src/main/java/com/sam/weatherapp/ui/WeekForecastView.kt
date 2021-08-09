package com.sam.weatherapp.ui

import com.sam.weatherapp.features.model.data_class.WeekWeatherDataModel

interface WeekForecastView {
    fun handleProgressBarVisibility(visibility: Int)
    fun onWeatherInfoFetchSuccess(weekWeatherDataModel: WeekWeatherDataModel)
    fun onWeatherInfoFetchFailure(errorMessage: String)
}