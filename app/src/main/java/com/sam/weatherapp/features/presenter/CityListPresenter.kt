package com.sam.weatherapp.features.presenter

import com.sam.weatherapp.features.model.data_class.City
import kotlinx.coroutines.CoroutineScope

interface CityListPresenter {
    fun fetchCityList(coroutineScope: CoroutineScope)
    fun fetchFavoriteCityList(coroutineScope: CoroutineScope, cityList: MutableList<City>? = null)
    fun deleteCityFromFavorites(coroutineScope: CoroutineScope, city: City)
    fun addCityToFavorites(coroutineScope: CoroutineScope, city: City)
}