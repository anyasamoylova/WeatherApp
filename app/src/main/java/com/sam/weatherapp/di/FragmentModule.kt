package com.sam.weatherapp.di

import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.model.WeatherInfoModelImpl
import com.sam.weatherapp.ui.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [DayForecastModule::class])
    abstract fun contributeDayForecastFragment(): DayForecastFragment

    @ContributesAndroidInjector(modules = [CityListModule::class])
    abstract fun contributeSearchFragment(): CityListFragment

    @ContributesAndroidInjector(modules = [MapsModule::class])
    abstract fun contributeMapsFragment(): MapFragment

    @ContributesAndroidInjector(modules = [WeekForecastModule::class])
    abstract fun contributeWeekForecastFragment(): WeekForecastFragment

    @Binds
    abstract fun bindModel(weatherInfoShowModelImpl: WeatherInfoModelImpl): WeatherInfoModel
}