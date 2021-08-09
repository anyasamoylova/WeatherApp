package com.sam.weatherapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sam.weatherapp.R
import com.sam.weatherapp.features.adapter.WeekForecastAdapter
import com.sam.weatherapp.features.model.data_class.WeekWeatherDataModel
import com.sam.weatherapp.features.presenter.WeatherInfoPresenter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_week_forecast.*
import javax.inject.Inject

class WeekForecastFragment : DaggerFragment(), WeekForecastView {
    private val TAG = "WeekForecastFragment"
    @Inject
    lateinit var presenter: WeatherInfoPresenter
    @Inject
    lateinit var todayAdapter: WeekForecastAdapter
    @Inject
    lateinit var weekAdapter: WeekForecastAdapter

    private lateinit var rvTodayForecast: RecyclerView
    private lateinit var rvWeekForecast: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherView: View
    private lateinit var errorView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val root = inflater.inflate(R.layout.fragment_week_forecast, container, false)

        progressBar = root.findViewById(R.id.progressBar)
        weatherView = root.findViewById(R.id.clMainContent)
        errorView = root.findViewById(R.id.stateDataLoadingError)

        rvTodayForecast = root.findViewById(R.id.rvTodayForecastByTime)
        rvWeekForecast = root.findViewById(R.id.rvWeekForecast)
        todayAdapter.cardViewId = R.layout.card_hour_forecast
        weekAdapter.cardViewId = R.layout.card_day_forecast
        rvTodayForecast.adapter = todayAdapter
        rvWeekForecast.adapter = weekAdapter

        val bundle = arguments
        if (bundle != null) {
            val latitude = bundle.getDouble("latitude", 0.0)
            val longitude = bundle.getDouble("longitude", 0.0)
            presenter.fetchWeekWeatherInfoByCoordinates(latitude, longitude)
        } else Toast.makeText(activity, "Bundle is null", Toast.LENGTH_LONG).show()

        return root
    }

    override fun handleProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
        when (visibility) {
            View.GONE -> weatherView.visibility = View.VISIBLE
            View.VISIBLE -> weatherView.visibility = View.GONE
        }
    }

    private fun setDataLoadingErrorView() {
        weatherView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    override fun onWeatherInfoFetchSuccess(weekWeatherDataModel: WeekWeatherDataModel) {
        tvDate.text = weekWeatherDataModel.date
        todayAdapter.updateContent(weekWeatherDataModel.hourlyWeather)
        weekAdapter.updateContent(weekWeatherDataModel.dailyWeather)
    }

    override fun onWeatherInfoFetchFailure(errorMessage: String) {
        Log.e(TAG, errorMessage)
        setDataLoadingErrorView()
    }


}