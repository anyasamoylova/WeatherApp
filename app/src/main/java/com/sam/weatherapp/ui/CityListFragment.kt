package com.sam.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.sam.weatherapp.R
import com.sam.weatherapp.features.adapter.CityAdapter
import com.sam.weatherapp.features.adapter.CityClickListener
import com.sam.weatherapp.features.adapter.FavoriteIconClickListener
import com.sam.weatherapp.features.model.data_class.City
import com.sam.weatherapp.features.presenter.CityListPresenter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Shows list of cities and asks presenter for info when click on specific city
 */
class CityListFragment : DaggerFragment(), CityListView, CoroutineScope {
    companion object {
        const val ONLY_FAVORITE_CITIES = "onlyFavoriteCities"
    }
    @Inject
    lateinit var presenter: CityListPresenter
    @Inject
    lateinit var adapter: CityAdapter
    lateinit var navController: NavController

    private lateinit var rv: RecyclerView
    lateinit var onFinishInterface: FragmentInterface

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        job = Job()

        val onlyFavoriteCities: Boolean = arguments?.getBoolean(ONLY_FAVORITE_CITIES, false)?: false

        val root = inflater.inflate(R.layout.fragment_city_list, container, false)
        rv = root.findViewById(R.id.rvCitiesList)

        navController = (requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment).navController

        adapter.setCityClickListener(object : CityClickListener{
            override fun onCityClick(city: City) {
                val bundle = Bundle()
                bundle.putInt(DayForecastFragment.CITY_ID, city.id)
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.dayForecastFragment, true)
                    .build()

                navController.navigate(
                    R.id.action_cityListFragment_to_dayForecastFragment,
                    bundle,
                    navOptions
                )
//                        tvCityName.visibility = View.VISIBLE

        }})
        //listener for clicking at favorite icon
        adapter.setFavoriteIconClickListener(object : FavoriteIconClickListener{
            override fun onIconClick(city: City) {
                city.isFavorite = !city.isFavorite
                //add/delete city to/from db
                if (city.isFavorite) {
                    presenter.addCityToFavorites(this@CityListFragment, city)
                } else {
                    presenter.deleteCityFromFavorites(this@CityListFragment, city)
                }
            }

        })
        rv.adapter = adapter

        if (onlyFavoriteCities)
            presenter.fetchFavoriteCityList(this)
        else
            presenter.fetchCityList(this)
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        //adapter.clear()
        job.cancel()
    }

    fun setOnFinishAction(fragmentInterface: FragmentInterface) {
        this.onFinishInterface = fragmentInterface
    }

    override fun onCityListFetchSuccess(cityList: MutableList<City>) {
        adapter.updateContent(cityList)
    }

    override fun onCityListFetchFailure(errorMessage: String) {
        //clear adapter because there is no suitable cities
        //adapter.clear()
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
    }

}