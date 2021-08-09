package com.sam.weatherapp.features.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sam.weatherapp.R
import com.sam.weatherapp.features.model.data_class.Weather
import com.sam.weatherapp.features.model.data_class.WeatherDataModel
import kotlinx.android.synthetic.main.fragment_day_forecast.*
import javax.inject.Inject

class WeekForecastAdapter @Inject constructor(val context: Context): RecyclerView.Adapter<WeekForecastAdapter.ForecastViewHolder>() {

    private var weatherData: List<WeatherDataModel> = listOf()
    var cardViewId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val v = LayoutInflater.from(context).inflate(cardViewId!!, parent, false)
        return ForecastViewHolder(v)

    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    fun updateContent(newData: MutableList<WeatherDataModel>) {
        weatherData = newData
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val ivWeatherIcon: ImageView = itemView.findViewById(R.id.ivWeatherIcon)
        private val tvTemperature: TextView = itemView.findViewById(R.id.tvTemperature)

        fun bind (weatherData: WeatherDataModel){
            tvDate.text = weatherData.dateTime
            val iconName: String = "d${weatherData.weatherConditionIconUrl.substring(0, 2)}"
            ivWeatherIcon.setImageResource(context.resources.getIdentifier(iconName, "drawable", context.packageName))
            tvTemperature.text = weatherData.temperature
        }
    }

}