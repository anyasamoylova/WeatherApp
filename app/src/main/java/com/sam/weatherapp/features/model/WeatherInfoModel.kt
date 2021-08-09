package com.sam.weatherapp.features.model

import com.sam.weatherapp.common.RequestCompleteListener
import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.features.model.response.WeatherResponse
import com.sam.weatherapp.features.model.response.WeatherResponseList
import com.sam.weatherapp.features.model.response.WeekWeatherResponse
import kotlinx.coroutines.CoroutineScope

interface WeatherInfoModel {
    fun getCityList(callBack: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfoByCityId(cityId: Int, callBack: RequestCompleteListener<WeatherResponse>)
    fun getWeatherInfoByCoordinates(latitude: Double, longitude: Double, callBack: RequestCompleteListener<WeatherResponseList>)
    fun getWeekWeatherInfoByCoordinates(latitude: Double, longitude: Double, callBack: RequestCompleteListener<WeekWeatherResponse>)
    fun getFavoriteCityList(coroutineScope: CoroutineScope, callBack:RequestCompleteListener<MutableList<City>?>)
    fun deleteCityFromFavorites(coroutineScope: CoroutineScope, city: City)
    fun addCityToFavorites(coroutineScope: CoroutineScope, city: City)
}