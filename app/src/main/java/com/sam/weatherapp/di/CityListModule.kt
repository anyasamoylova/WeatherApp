package com.sam.weatherapp.di

import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.presenter.CityListPresenter
import com.sam.weatherapp.features.presenter.CityListPresenterImpl
import com.sam.weatherapp.ui.CityListFragment
import com.sam.weatherapp.ui.CityListView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CityListModule {
    @Binds
    abstract fun bindView(cityListFragment: CityListFragment): CityListView

    companion object {
        @Provides
        fun providePresenter(
            view: CityListView,
            model: WeatherInfoModel
        ): CityListPresenter {
            return CityListPresenterImpl(view, model)
        }
    }
}