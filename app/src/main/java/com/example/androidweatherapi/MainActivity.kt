package com.example.androidweatherapi

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidweatherapi.WeatherApi.NearestCitiesResponse.NearCitiesResponse
import com.example.androidweatherapi.WeatherApi.NearestCitiesResponse.NearCity
import com.example.androidweatherapi.WeatherApi.WeatherRepository
import com.example.androidweatherapi.WeatherApi.WeatherResponse.WeatherResponse
import com.example.androidweatherapi.databinding.ActivityMainBinding
import com.example.androidweatherapi.recyclerview.City
import com.example.androidweatherapi.recyclerview.CityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.util.*

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val repository by lazy {
        WeatherRepository()
    }

    private val CITY_ID = "CITY_ID"

    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var nearestCities: MutableList<City> = mutableListOf()

    private var cityAdapter: CityAdapter? = null

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
            } else {
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.svSearch.setOnQueryTextListener(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationAccess()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        lifecycleScope.launch {
            var response: WeatherResponse? = null
            try {
                response = getCityByName(p0 ?: "")
            } catch (ex: Exception) {
                Log.e("ds", ex.message.toString())
            }

            if (response != null)
                navigateToDetail(response)
            else
                Snackbar.make(binding.root, "City not found", Snackbar.LENGTH_LONG).show()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun getLocation(): Unit {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var locationTask = fusedLocationProviderClient.lastLocation

            locationTask.addOnSuccessListener {
                it?.let {
                    Log.e("Location - ", "Longitude: ${it.longitude}, latitude: ${it.latitude}")
                    lifecycleScope.launch {
                        var cities = findNearCities(it)
                        delay(1000)
                        setAdapter(cities)
                    }

                    lifecycleScope.launch {
                        delay(2 * 1000)
                    }
                }
                if (it == null) {
                    Log.e("location", "is null")
                }
            }
            locationTask.addOnFailureListener {
                Log.e("location exp.", it.message.toString())
            }
        }
    }

    private suspend fun findNearCities(location: Location): List<NearCity> {
        var response: NearCitiesResponse? = null
        lifecycleScope.launch {
            response = repository.getNearCities(location.longitude, location.latitude, 10)
        }.join()
        return response?.list ?: listOf()
    }

    private fun setAdapter(cities: List<NearCity>) {
        cityAdapter = CityAdapter(cities) {
            startActivity(
                Intent(
                    this,
                    DetailWeatherActivity::class.java
                ).apply {
                    putExtra(CITY_ID, it)
                }
            )
        }
        binding.rvCities?.run {
            adapter = cityAdapter
        }
    }

    private fun navigateToDetail(response: WeatherResponse) {
        startActivity(Intent(
            this,
            DetailWeatherActivity::class.java
        ).apply {
            putExtra(CITY_ID, response.id)
        })
    }

    private suspend fun getCityByName(name: String): WeatherResponse? {
        var response: WeatherResponse? = null
        lifecycleScope.launch {
            try {
                response = repository.getWeather(name)
            } catch (ex: Exception){
                Log.e("", ex.message.toString())
            }
        }.join()
        val t = 1
        return response
    }
}
