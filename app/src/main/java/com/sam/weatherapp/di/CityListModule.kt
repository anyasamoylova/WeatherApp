package com.sam.weatherapp.di

import android.content.Context
import com.sam.weatherapp.features.adapter.CityAdapter
import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.presenter.CityListPresenter
import com.sam.weatherapp.features.presenter.CityListPresenterImpl
import com.sam.weatherapp.ui.CityListFragment
import com.sam.weatherapp.ui.CityListView
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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