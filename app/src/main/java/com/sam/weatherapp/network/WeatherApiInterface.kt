package com.sam.weatherapp.network

import com.sam.weatherapp.features.model.response.WeatherResponse
import com.sam.weatherapp.features.model.response.WeatherResponseList
import com.sam.weatherapp.features.model.response.WeekWeatherResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("weather")
    fun callApiForWeatherInfoByCityId(@Query("id") cityId: Int): Call<WeatherResponse>

    @GET("find")
    fun callApiForWeatherInfoByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int = 1)
    : Call<WeatherResponseList>

    @GET("onecall")
    fun callApiForWeekWeatherInfoByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") param: String = "minutely,alerts"
    ): Call<WeekWeatherResponse>
}