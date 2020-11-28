package com.elnahas.task.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.elnahas.task.R
import com.elnahas.task.data.model.RoutePath
import com.elnahas.task.util.Constants.POLYLINE_COLOR
import com.elnahas.task.util.Constants.POLYLINE_WIDTH
import com.elnahas.task.ui.fragments.AboutUsDialogFragment
import com.elnahas.task.util.Resource
import com.elnahas.task.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*



@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private var pathPoints : MutableList<LatLng> = mutableListOf()

    val mainViewModel: MainViewModel by viewModels()


    private var map: GoogleMap? = null

    lateinit var polylineOptions : PolylineOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
        }

        polylineOptions = PolylineOptions()
            .color(POLYLINE_COLOR)
            .width(POLYLINE_WIDTH)

        mainViewModel.routeLiveData.observe(this@MainActivity, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { routeResponse ->
                        addPolylines(routeResponse.InnerData.user.bus.route.routePath)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(MainActivity@this , message , Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        })

        img_aboutus.setOnClickListener {
            AboutUsDialogFragment.newInstance().show(supportFragmentManager, AboutUsDialogFragment.TAG)
        }
    }

    private fun addPolylines(routeList:List<RoutePath>) {

        pathPoints.clear()

        for(polyline in routeList)
            pathPoints.add(LatLng(polyline.lat , polyline.lng))

        polylineOptions.addAll(pathPoints)

        map?.clear()
        map?.addPolyline(polylineOptions)

        zoomToSeeWholeTrack(routeList)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    private fun zoomToSeeWholeTrack(pathPoints:List<RoutePath>) {
        val bounds = LatLngBounds.Builder()
        for(pos in pathPoints) {
            bounds.include(LatLng(pos.lat , pos.lng))

        }

        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.05f).toInt()
            )
        )
    }

}