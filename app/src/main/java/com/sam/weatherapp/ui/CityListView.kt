package com.sam.weatherapp.ui

import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.features.model.data_class.WeatherDataModel

interface CityListView {
    fun onCityListFetchSuccess(cityList: MutableList<City>)
    fun onCityListFetchFailure(errorMessage: String)
}