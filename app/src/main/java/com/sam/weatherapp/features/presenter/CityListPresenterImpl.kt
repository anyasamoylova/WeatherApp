package com.sam.weatherapp.features.presenter

import com.sam.weatherapp.common.RequestCompleteListener
import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.ui.CityListView
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class
CityListPresenterImpl @Inject constructor(
    private var view: CityListView?,
    private val model: WeatherInfoModel
) : CityListPresenter {

    override fun fetchCityList(coroutineScope: CoroutineScope) {
        model.getCityList(object: RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                fetchFavoriteCityList(coroutineScope, data)
            }

            override fun onRequestFailed(errorMessage: String) {
                view?.onCityListFetchFailure(errorMessage)
            }

        })
    }

    override fun fetchFavoriteCityList(coroutineScope: CoroutineScope, cityList: MutableList<City>?) {
        model.getFavoriteCityList(coroutineScope, object: RequestCompleteListener<MutableList<City>?>{
            override fun onRequestSuccess(favoriteCities: MutableList<City>?) {
                if (cityList != null && favoriteCities != null) {
                    //map data about all cities with data about favorite cities
                    cityList.forEach {
                        if (favoriteCities.contains(it))
                            it.isFavorite = true
                    }
                    view?.onCityListFetchSuccess(cityList)
                }
                else if (cityList != null && favoriteCities == null)
                    view?.onCityListFetchSuccess(cityList)
                else if (cityList == null && favoriteCities != null) {
                    view?.onCityListFetchSuccess(favoriteCities)
                } else
                    view?.onCityListFetchFailure("There is no favorite cities yet")
            }
            override fun onRequestFailed(errorMessage: String) {
                view?.onCityListFetchFailure(errorMessage)
            }

        })
    }

    override fun deleteCityFromFavorites(coroutineScope: CoroutineScope, city: City) {
        model.deleteCityFromFavorites(coroutineScope, city)
    }

    override fun addCityToFavorites(coroutineScope: CoroutineScope, city: City) {
        model.addCityToFavorites(coroutineScope, city)
    }

}