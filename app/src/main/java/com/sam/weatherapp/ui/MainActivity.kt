package com.sam.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sam.weatherapp.R
import com.sam.weatherapp.features.adapter.CityAdapter
import com.sam.weatherapp.ui.CityListFragment.Companion.ONLY_FAVORITE_CITIES
import com.sam.weatherapp.ui.DayForecastFragment.Companion.IS_FORECAST_ENABLED
import com.sam.weatherapp.ui.DayForecastFragment.Companion.LATITUDE
import com.sam.weatherapp.ui.DayForecastFragment.Companion.LONGITUDE
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(){
    companion object {
        const val PERMISSION_FINE_LOCATION = 66
    }

    @Inject
    lateinit var cityAdapter: CityAdapter
    private lateinit var navController: NavController

    //Google's API for location service. It provides the last known location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = (supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment).navController
        createToolbar()
        prepareToLocationRequest()
    }

    private fun createToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setCustomView(R.layout.custom_menu)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.title = null
        val customMenu: View? = supportActionBar?.customView

        if (customMenu != null) {
            val rlToolbarStandard = customMenu.findViewById<RelativeLayout>(R.id.rlToolbarStandard)
            val rlToolbarBack = customMenu.findViewById<RelativeLayout>(R.id.rlToolbarBack)

            val ivActionMap = customMenu.findViewById<ImageView>(R.id.ivActionMap)
            val svActionSearch = customMenu.findViewById<SearchView>(R.id.ivActionSearch)
            val ivActionFavorite = customMenu.findViewById<ImageView>(R.id.ivActionFavorite)
            val tvCityName = customMenu.findViewById<TextView>(R.id.tvCityName)
            val ivActionBack = customMenu.findViewById<ImageView>(R.id.ivActionBack)

            navController.addOnDestinationChangedListener { _,destination,_ ->
                if (destination.id == R.id.dayForecastFragment) {
                    supportActionBar?.show()
                    (svActionSearch as SearchView).onActionViewCollapsed()
                    tvCityName.visibility = View.VISIBLE
                    ivActionMap.visibility = View.VISIBLE
                    ivActionFavorite.visibility = View.VISIBLE

                    rlToolbarStandard.visibility = View.VISIBLE
                    rlToolbarBack.visibility = View.GONE
                }
                if (destination.id == R.id.weekForecastFragment) {
                    rlToolbarStandard.visibility = View.GONE
                    rlToolbarBack.visibility = View.VISIBLE
                }
            }

            ivActionMap.setOnClickListener {
                supportActionBar?.hide()
                navController.navigate(R.id.action_dayForecastFragment_to_mapFragment)
            }

            svActionSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                //set filter to the adapter to show only necessary cities
                override fun onQueryTextChange(newText: String?): Boolean {
                    cityAdapter.setFilter(newText ?: "")
                    return true
                }

            })

            svActionSearch.setOnSearchClickListener {
                tvCityName.visibility = View.GONE
                ivActionMap.visibility = View.GONE
                ivActionFavorite.visibility = View.GONE

                val bundle = Bundle()
                bundle.putBoolean(ONLY_FAVORITE_CITIES, false)

                navController.navigate(R.id.action_dayForecastFragment_to_cityListFragment, bundle)
            }

            svActionSearch.setOnCloseListener {
                navController.popBackStack()
            }

            ivActionFavorite.setOnClickListener {
                val bundle = Bundle()
                bundle.putBoolean(ONLY_FAVORITE_CITIES, true)

                navController.navigate(R.id.action_dayForecastFragment_to_cityListFragment, bundle)
                rlToolbarStandard.visibility = View.GONE
                rlToolbarBack.visibility = View.VISIBLE
            }

            ivActionBack.setOnClickListener {
                navController.popBackStack()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            PERMISSION_FINE_LOCATION -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "This app requires a location permission to show you weather around you",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun prepareToLocationRequest() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_FINE_LOCATION)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                    if (it != null) {
                        val bundle = Bundle()
                        bundle.putDouble(LATITUDE, it.latitude)
                        bundle.putDouble(LONGITUDE, it.longitude)
                        navController.navigate(R.id.dayForecastFragment, bundle)
                    } else (
                      setUnknownLocationState()
                    )
                }
        } else {
            setUnknownLocationState()
        }
    }

    private fun setUnknownLocationState() {
        val bundle = Bundle()
        bundle.putBoolean(IS_FORECAST_ENABLED, false)
        navController.navigate(R.id.dayForecastFragment, bundle)
    }
}