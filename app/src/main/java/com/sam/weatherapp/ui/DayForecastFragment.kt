package com.sam.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sam.weatherapp.R
import com.sam.weatherapp.features.model.data_class.Coordinate
import com.sam.weatherapp.features.model.data_class.WeatherDataModel
import com.sam.weatherapp.features.presenter.WeatherInfoPresenter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.state_weather.*
import javax.inject.Inject

class DayForecastFragment : DaggerFragment(), DayForecastView {
    companion object {
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val CITY_ID = "cityId"
        const val IS_FORECAST_ENABLED = "isForecastEnabled"
    }

    @Inject
    lateinit var presenter: WeatherInfoPresenter
    private lateinit var navController: NavController

    private lateinit var btnWeekForecast: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherView: View
    private lateinit var unknownLocationView: View
    private var coordinate: Coordinate = Coordinate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_day_forecast, container, false)
        navController = (requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment).navController
        progressBar = root.findViewById(R.id.progressBar)
        weatherView = root.findViewById(R.id.stateWeather)
        unknownLocationView = root.findViewById(R.id.stateLocationUnknown)
        btnWeekForecast = root.findViewById(R.id.btnWeekForecast)
        btnWeekForecast.setOnClickListener {
            val bundle = Bundle()
            bundle.putDouble(LATITUDE, coordinate.latitude)
            bundle.putDouble(LONGITUDE, coordinate.longitude)
            navController.navigate(R.id.action_dayForecastFragment_to_weekForecastFragment, bundle)
        }

        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(CITY_ID))
                presenter.fetchWeatherInfoByCityId(bundle.getInt(CITY_ID))
            else if (bundle.containsKey(LATITUDE)) {
                val latitude = bundle.getDouble(LATITUDE, 0.0)
                val longitude = bundle.getDouble(LONGITUDE, 0.0)
                presenter.fetchWeatherInfoByCoordinates(latitude, longitude)
            } else if (bundle.containsKey(IS_FORECAST_ENABLED) && !bundle.getBoolean(IS_FORECAST_ENABLED)) {
                setUnknownLocationView()
            }
        }
        return root
    }

    private fun setUnknownLocationView() {
        progressBar.visibility = View.GONE
        weatherView.visibility = View.GONE
        unknownLocationView.visibility = View.VISIBLE
    }

    override fun handleProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
        when (visibility) {
            View.GONE -> weatherView.visibility = View.VISIBLE
            View.VISIBLE -> weatherView.visibility = View.GONE
        }

    }

    override fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel, coordinate: Coordinate) {
        tvTodayDate.text = weatherDataModel.dateTime
        tvTemperature.text = weatherDataModel.temperature
        if (weatherDataModel.description.isEmpty())
            tvWeatherDescription.visibility = View.GONE
        else
            tvWeatherDescription.text = weatherDataModel.description
        tvHumidity.text = weatherDataModel.humidity
        tvWindSpeed.text = weatherDataModel.windSpeed
        val iconName = "d${weatherDataModel.weatherConditionIconUrl.substring(0, 2)}"
        ivForecastIcon.setImageResource(requireContext().resources.getIdentifier(iconName, "drawable", requireContext().packageName))
        this.coordinate = coordinate
        (requireActivity() as AppCompatActivity).supportActionBar?.customView?.findViewById<TextView>(R.id.tvCityName)?.text = weatherDataModel.cityAndCountry

    }

    override fun onWeatherInfoFetchFailure(errorMessage: String) {
        setUnknownLocationView()
    }

}