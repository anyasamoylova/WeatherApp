package com.sam.weatherapp.ui

interface FragmentInterface {
    fun onFinishSearchFragment(cityId: Int)
    fun openWeekForecast(latitude: Double, longitude: Double)
}