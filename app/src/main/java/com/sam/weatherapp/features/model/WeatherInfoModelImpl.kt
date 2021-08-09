package com.sam.weatherapp.features.model

import android.content.Context
import com.sam.weatherapp.common.RequestCompleteListener
import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.features.model.response.WeatherResponse
import com.sam.weatherapp.features.model.response.WeatherResponseList
import com.sam.weatherapp.features.model.response.WeekWeatherResponse
import com.sam.weatherapp.network.WeatherApiInterface
import com.sam.weatherapp.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherInfoModelImpl @Inject constructor(
    private val context: Context,
    private val apiInterface: WeatherApiInterface,
    private val database: AppDatabase
) : WeatherInfoModel{
    //fetch city list from local source
    override fun getCityList(callBack: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")
            val buffer = ByteArray(stream.available())
            stream.read(buffer)
            stream.close()
            val content = String(buffer)

            val json = Json{ ignoreUnknownKeys = true}
            val cityList: MutableList<City> = json.decodeFromString(content)

            callBack.onRequestSuccess(cityList)
        } catch (e: IOException) {
            e.printStackTrace()
            callBack.onRequestFailed(e.localizedMessage!!)
        }
    }

    //fetch weather info from network and let presenter know about results
    override fun getWeatherInfoByCityId(cityId: Int, callback: RequestCompleteListener<WeatherResponse>) {
        val call: Call<WeatherResponse> = apiInterface.callApiForWeatherInfoByCityId(cityId)
        call.enqueue(object: Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.body() != null) {
                    callback.onRequestSuccess(response.body()!!)
                } else {
                    callback.onRequestFailed(response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }

        }
        )
    }

    override fun getWeatherInfoByCoordinates(
        latitude: Double,
        longitude: Double,
        callback: RequestCompleteListener<WeatherResponseList>,
    ) {
        val call: Call<WeatherResponseList> = apiInterface.callApiForWeatherInfoByCoordinates(latitude, longitude)
        call.enqueue(object: Callback<WeatherResponseList> {
            override fun onResponse(
                call: Call<WeatherResponseList>,
                response: Response<WeatherResponseList>
            ) {
                if (response.body() != null) {
                    callback.onRequestSuccess(response.body()!!)
                } else {
                    callback.onRequestFailed(response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponseList>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }

        }
        )
    }

    override fun getWeekWeatherInfoByCoordinates(
        latitude: Double,
        longitude: Double,
        callback: RequestCompleteListener<WeekWeatherResponse>,
    ) {
        val call: Call<WeekWeatherResponse> = apiInterface.callApiForWeekWeatherInfoByCoordinates(latitude, longitude)
        call.enqueue(object: Callback<WeekWeatherResponse> {
            override fun onResponse(
                call: Call<WeekWeatherResponse>,
                response: Response<WeekWeatherResponse>,
            ) {
                if (response.body() != null) {
                    callback.onRequestSuccess(response.body()!!)
                } else {
                    callback.onRequestFailed(response.message())
                }
            }

            override fun onFailure(call: Call<WeekWeatherResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }

        })
    }

    override fun getFavoriteCityList(
        coroutineScope: CoroutineScope,
        callBack: RequestCompleteListener<MutableList<City>?>,
    ) {
        val dataJob = coroutineScope.async {
            database.cityDao().getAll()
        }
        dataJob.invokeOnCompletion { cause ->
            if (cause != null) {
                //something happened and job didn't finished success
                callBack.onRequestFailed(cause.localizedMessage!!)
            } else {
                callBack.onRequestSuccess(dataJob.getCompleted() as MutableList<City>)
            }
        }
    }

    override fun deleteCityFromFavorites(coroutineScope: CoroutineScope, city: City) {
        coroutineScope.async {
            database.cityDao().delete(city)
        }
    }

    override fun addCityToFavorites(coroutineScope: CoroutineScope, city: City) {
        coroutineScope.async {
            database.cityDao().insert(city)
        }
    }
}