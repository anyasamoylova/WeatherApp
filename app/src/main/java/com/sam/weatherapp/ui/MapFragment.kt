package com.sam.weatherapp.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sam.weatherapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

class MapFragment : DaggerFragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    lateinit var navController: NavController
    private lateinit var googleMap: GoogleMap
    lateinit var btnBack: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        btnBack = view.findViewById(R.id.btnBack)
        return view
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        navController = (requireActivity().supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment).navController
        btnBack.setOnClickListener {
            navController.popBackStack()
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //TODO to move permission request here
        this.googleMap = googleMap
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            createLocationButton()
        }

        googleMap.setOnMapClickListener(this)
    }

    override fun onMapClick(coord: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(coord)
        googleMap.clear()
        googleMap.addMarker(markerOptions)
        createAlertDialog(coord)
    }

    private fun createAlertDialog(coord: LatLng) {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Do you want to see the weather for this point?")
            .setPositiveButton("Yes") {_, _ ->
                run {
                    val bundle = Bundle()
                    bundle.putDouble("latitude", coord.latitude)
                    bundle.putDouble("longitude", coord.longitude)
                    //clear backstack
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.dayForecastFragment, true)
                        .build()

                    navController.navigate(
                        R.id.action_mapFragment_to_dayForecastFragment,
                        bundle,
                        navOptions
                    )

                }
            }
            .setNegativeButton("No", null)
            .create()
        dialog.show()
    }

    private fun createLocationButton() {
        val mapView = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).requireView()
        val btnLocation= (mapView.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        val rlParams = btnLocation.layoutParams as (RelativeLayout.LayoutParams)
        rlParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlParams.setMargins(0,0,0,30)
    }


}