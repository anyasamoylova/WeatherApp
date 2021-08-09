package com.sam.weatherapp.features.presenter

import android.view.View
import com.sam.weatherapp.common.RequestCompleteListener
import com.sam.weatherapp.features.model.WeatherInfoModel
import com.sam.weatherapp.features.model.data_class.WeatherDataModel
import com.sam.weatherapp.features.model.data_class.WeekWeatherDataModel
import com.sam.weatherapp.features.model.response.WeatherResponse
import com.sam.weatherapp.features.model.response.WeatherResponseList
import com.sam.weatherapp.features.model.response.WeekWeatherResponse
import com.sam.weatherapp.ui.DayForecastView
import com.sam.weatherapp.ui.WeekForecastView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherInfoPresenterImpl @Inject constructor(
    private var dayForecastView: DayForecastView? = null,
    private var weekForecastView: WeekForecastView? = null,
    private val model: WeatherInfoModel
) : WeatherInfoPresenter {
    override fun fetchWeatherInfoByCityId(cityId: Int) {
        dayForecastView?.handleProgressBarVisibility(View.VISIBLE)
        model.getWeatherInfoByCityId(cityId, object : RequestCompleteListener<WeatherResponse> {
            override fun onRequestSuccess(data: WeatherResponse) {
                dayForecastView?.handleProgressBarVisibility(View.GONE)

                val weatherDataModel = WeatherDataModel(
                    dateTime = convertLongToDate(data.dateTime),
                    temperature = convertFahrenheitToCelsius(data.main.temperature),
                    cityAndCountry = "${data.cityName}, ${data.sys.country}",
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} km",
                    windSpeed = "${data.wind.speed} m/sec",
                    weatherConditionIconUrl = "${data.weather[0].icon}",
                    description = data.weather[0].main
                )

                dayForecastView?.onWeatherInfoFetchSuccess(weatherDataModel, data.coordinate)
            }

            override fun onRequestFailed(errorMessage: String) {
                dayForecastView?.handleProgressBarVisibility(View.GONE)
                dayForecastView?.onWeatherInfoFetchFailure(errorMessage)
            }
        })
    }

    override fun fetchWeatherInfoByCoordinates(latitude: Double, longitude: Double) {
        dayForecastView?.handleProgressBarVisibility(View.VISIBLE)
        model.getWeatherInfoByCoordinates(latitude, longitude, object : RequestCompleteListener<WeatherResponseList> {
            override fun onRequestSuccess(dataList: WeatherResponseList) {
                dayForecastView?.handleProgressBarVisibility(View.GONE)
                val data = dataList.citiesWithWeather[0]
                val weatherInfoDataModel = WeatherDataModel(
                    coordinates = data.coordinate.toString(),
                    dateTime = convertLongToDate(data.dateTime),
                    temperature = convertFahrenheitToCelsius(data.main.temperature),
                    cityAndCountry = "${data.cityName}, ${data.sys.country}",
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    windSpeed = "${data.wind.speed} m/s",
                    weatherConditionIconUrl = data.weather[0].icon,
                    description = data.weather[0].main
                )
                dayForecastView?.onWeatherInfoFetchSuccess(weatherInfoDataModel, data.coordinate)
            }

            override fun onRequestFailed(errorMessage: String) {
                dayForecastView?.handleProgressBarVisibility(View.GONE)
                dayForecastView?.onWeatherInfoFetchFailure(errorMessage)
            }
        })
    }

    override fun fetchWeekWeatherInfoByCoordinates(latitude: Double, longitude: Double) {
        weekForecastView?.handleProgressBarVisibility(View.VISIBLE)
        model.getWeekWeatherInfoByCoordinates(latitude, longitude, object: RequestCompleteListener<WeekWeatherResponse>{
            override fun onRequestSuccess(data: WeekWeatherResponse) {
                weekForecastView?.handleProgressBarVisibility(View.GONE)
                val hourlyData = data.hourlyForecast
                val dailyData = data.dailyForecast
                val weekWeatherDataModel = WeekWeatherDataModel(convertLongToDate(data.hourlyForecast[0].dateTime))
                val nextDay = getNextDay(hourlyData[0].dateTime)
                hourlyData.forEach {
                    val dtInTimeZone = it.dateTime + TimeZone.getDefault().rawOffset/1000
                    if (dtInTimeZone < nextDay) { //get only today data (from now to 00:00)
                        weekWeatherDataModel.hourlyWeather.add(WeatherDataModel(
                            dateTime = convertLongToTime(it.dateTime),
                            temperature = convertFahrenheitToCelsius(it.temperature),
                            weatherConditionIconUrl = it.weather[0].icon,
                            weatherConditionIconDescription = it.weather[0].description
                        ))
                    }
                }
                dailyData.subList(1, dailyData.size).forEach {
                    weekWeatherDataModel.dailyWeather.add(WeatherDataModel(
                        dateTime = convertLongToDate(it.dateTime),
                        temperature = "${convertFahrenheitToCelsius(it.temperature.dayTemperature)} / ${convertFahrenheitToCelsius(it.temperature.nightTemperature)}",
                        weatherConditionIconUrl = it.weather[0].icon,
                        weatherConditionIconDescription = it.weather[0].description
                    ))
                }
                weekForecastView?.onWeatherInfoFetchSuccess(weekWeatherDataModel)
            }

            override fun onRequestFailed(errorMessage: String) {
                weekForecastView?.handleProgressBarVisibility(View.GONE)
                weekForecastView?.onWeatherInfoFetchFailure(errorMessage)
            }

        })
    }

    //convert long to date like "JUN, 5"
    fun convertLongToDate(timeMs: Long): String {
        val date = Date(timeMs*1000 + TimeZone.getDefault().rawOffset)
        val dateFormat = SimpleDateFormat("MMM, d", Locale.ENGLISH)
        return dateFormat.format(date)
    }

    //converts long to time like "12:42"
    fun convertLongToTime(timeMs: Long): String{
        val date = Date(timeMs*1000L)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(date)
    }

    fun convertFahrenheitToCelsius(temp: Double): String{
        return "${(temp - 273.15).toInt()}°C"
    }

    //returns time of the next day (00:00:00) include timezone
    fun getNextDay(curr: Long): Long {
        val currInTimeZone = curr + TimeZone.getDefault().rawOffset / 1000
        val days = currInTimeZone / (60 * 60 * 24) + 1
        return days * 60 * 60 * 24
    }

//    override fun detachView() {
//        view = null
//    }
}