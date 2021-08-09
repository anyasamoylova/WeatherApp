package com.sam.weatherapp.di

import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.presenter.WeatherInfoPresenter
import com.sam.weatherapp.features.presenter.WeatherInfoPresenterImpl
import com.sam.weatherapp.ui.DayForecastFragment
import com.sam.weatherapp.ui.DayForecastView
import com.sam.weatherapp.ui.WeekForecastFragment
import com.sam.weatherapp.ui.WeekForecastView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class DayForecastModule {

    @Binds
    abstract fun bindDayView(dayForecastFragment: DayForecastFragment): DayForecastView

    companion object {

        @Provides
        fun providePresenter(
            dayView: DayForecastView,
            model: WeatherInfoModel
        ): WeatherInfoPresenter {
            return WeatherInfoPresenterImpl(dayView, null, model)
        }
    }

}