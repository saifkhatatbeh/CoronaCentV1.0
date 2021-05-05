package com.v1.Tammeni.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.v1.Tammeni.Activities.IntroAppActivity
import com.v1.Tammeni.Activities.TestNowActivity
import com.v1.Tammeni.Connection.NetworkConnection
import com.v1.Tammeni.R
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.custom_toast_negative.*
import kotlinx.android.synthetic.main.custom_toast_negative.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_intro_app_slider_3.*
import java.util.*


class Intro_app_slider_3 : Fragment() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private var PERMISSION_ID = 1000
    var a = ""
    var lc = ""
    var x = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_app_slider_3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)


        val mn: IntroAppActivity? = activity as IntroAppActivity?

        fusedLocationProviderClient = activity?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!


        val nConnection = NetworkConnection(requireActivity().applicationContext)

        nConnection.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->
            if (isConnected) {

                getLastLocation()

            } else {
                Toast(activity).apply {
                    customToast_negative.toast_text.text =
                        getString(R.string.No_Internet_Connection)
                    view = customToast_negative
                }.show()
            }
        })
        autoRefresh()
        isRefresh()
    }


    private fun autoRefresh() {
        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)

        Handler().postDelayed({

            activity?.applicationContext?.let { NetworkConnection(it) }
                ?.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->

                    if (isConnected) {
                        lc = "true"
                        getLastLocation()


                    } else {

                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                        SwipeRefresh_intro_app_page_3.isRefreshing = false


                    }

                })


        }, 17500)

    }

    private fun isRefresh() {
        val customToast_negative: View =
            layoutInflater.inflate(R.layout.custom_toast_negative, fl_wrapper_fill_all_fields_toast)
        SwipeRefresh_intro_app_page_3.setOnRefreshListener {

            activity?.applicationContext?.let { NetworkConnection(it) }
                ?.observe(requireActivity(), androidx.lifecycle.Observer { isConnected ->

                    if (isConnected) {
                        lc = "true"
                        getLastLocation()


                    } else {

                        Toast(activity).apply {
                            customToast_negative.toast_text.text =
                                getString(R.string.No_Internet_Connection)
                            view = customToast_negative
                        }.show()

                        SwipeRefresh_intro_app_page_3.isRefreshing = false

                    }

                })

        }

    }

    //    Location Functions----------------------------------------------------------------------------------------------------------------------------------------------------
    private fun getLastLocation() {

        val mn: IntroAppActivity? = activity as IntroAppActivity?

        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener() { task ->
                    val location = task.result
                    if (location == null) {
                        getNewLocation()
                    } else {

                        a = getCountryName(location.latitude, location.longitude)
                        location_enabled_image.visibility = View.VISIBLE
                        location_enabled_text.visibility = View.VISIBLE

                        location_disabled_image.visibility = View.GONE
                        location_disabled_text1.visibility = View.GONE
                        location_disabled_text2.visibility = View.GONE
                        if_location_is_on_text.visibility = View.GONE

                        mn?.locationCheck("true")
                    }
                }
            } else {
                lc = "false"
                location_enabled_image.visibility = View.GONE
                location_enabled_text.visibility = View.GONE

                location_disabled_image.visibility = View.VISIBLE
                location_disabled_text1.visibility = View.VISIBLE
                location_disabled_text2.visibility = View.VISIBLE
                if_location_is_on_text.visibility = View.VISIBLE


            }
            SwipeRefresh_intro_app_page_3.isRefreshing = false

        } else {
            requestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation = p0.lastLocation
            a = getCountryName(lastLocation.latitude, lastLocation.longitude)

            val mn: IntroAppActivity? = activity as IntroAppActivity?

            mn?.locationCheck("true")

            location_enabled_image.visibility = View.VISIBLE
            location_enabled_text.visibility = View.VISIBLE

            location_disabled_image.visibility = View.GONE
            location_disabled_text1.visibility = View.GONE
            location_disabled_text2.visibility = View.GONE
            if_location_is_on_text.visibility = View.GONE
        }
    }

    private fun checkPermission(): Boolean {

        if (
            activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED ||
            activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            SwipeRefresh_intro_app_page_3.isRefreshing = false
            return true
        }

        return false
    }

    private fun requestPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), PERMISSION_ID
            )
        }
        SwipeRefresh_intro_app_page_3.isRefreshing = false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        SwipeRefresh_intro_app_page_3.isRefreshing = false
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    private fun getCountryName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(activity, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        a = address[0].countryName
        return a
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You Have Permission")
                SwipeRefresh_intro_app_page_3.isRefreshing = false
            }
        }
    }

//  End of Location Functions-------------------------------------------------------------------------------------------------------------------------------------------------

}