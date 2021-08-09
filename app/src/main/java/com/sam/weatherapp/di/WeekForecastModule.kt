package com.sam.weatherapp.di

import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.presenter.WeatherInfoPresenter
import com.sam.weatherapp.features.presenter.WeatherInfoPresenterImpl
import com.sam.weatherapp.ui.WeekForecastFragment
import com.sam.weatherapp.ui.WeekForecastView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class WeekForecastModule {
    @Binds
    abstract fun bindWeekView(weekForecastFragment: WeekForecastFragment): WeekForecastView

    companion object {

        @Provides
        fun providePresenter(
            weekView: WeekForecastView,
            model: WeatherInfoModel
        ): WeatherInfoPresenter {
            return WeatherInfoPresenterImpl(null, weekView, model)
        }
    }
}