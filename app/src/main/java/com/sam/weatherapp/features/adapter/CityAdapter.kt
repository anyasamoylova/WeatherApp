package com.sam.weatherapp.features.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sam.weatherapp.R
import com.sam.weatherapp.features.model.data_class.City
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityAdapter @Inject constructor(val context: Context): RecyclerView.Adapter<CityAdapter.CityViewHolder>(){
    private lateinit var clickListener: CityClickListener
    private lateinit var iconClickListener: FavoriteIconClickListener
    private var cities: List<City> = listOf()
    private var filteredCities: List<City> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.city_item, parent, false)
        return CityViewHolder(v)
    }

    override fun getItemCount(): Int {
        return filteredCities.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(filteredCities[position])
    }

    fun updateContent(newCities: MutableList<City>) {
        cities = newCities
        filteredCities = cities
        notifyDataSetChanged()
    }

    fun setCityClickListener(cityClickListener: CityClickListener) {
        this.clickListener = cityClickListener
    }

    fun setFavoriteIconClickListener(iconClickListener: FavoriteIconClickListener) {
        this.iconClickListener = iconClickListener
    }

    //calls every time when search window changes
    fun setFilter(filter: String) {
        filteredCities = cities.filter { city -> city.name.startsWith(filter, true)}
        notifyDataSetChanged()
    }

    fun clear() {
        cities = emptyList()
        filteredCities = emptyList()
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCityName: TextView = itemView.findViewById(R.id.tvCityName)
        private val ivFavorite: ImageView = itemView.findViewById(R.id.ivFavorite)

        private lateinit var city: City

        init{
            itemView.setOnClickListener{
                clickListener.onCityClick(city)
            }
            //click on favorite icon
            ivFavorite.setOnClickListener {
                iconClickListener.onIconClick(city)
                notifyItemChanged(absoluteAdapterPosition)
            }
        }

        fun bind (city: City) {
            this.city = city
            tvCityName.text = city.name
            if (city.isFavorite)
                ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            else
                ivFavorite.setImageResource(R.drawable.ic_toolbar_favorite)
        }

    }
}