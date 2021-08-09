package com.sam.weatherapp.features.adapter

import com.sam.weatherapp.features.model.data_class.City

interface CityClickListener {
    fun onCityClick(city: City)
}

interface FavoriteIconClickListener {
    fun onIconClick(city: City)
}