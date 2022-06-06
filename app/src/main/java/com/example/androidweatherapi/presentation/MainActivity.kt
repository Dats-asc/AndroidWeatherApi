package com.example.androidweatherapi.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.androidweatherapi.databinding.ActivityMainBinding
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.presentation.cities.CityAdapter
import com.example.androidweatherapi.utils.AppViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    companion object {
        private const val CITY_ID = "CITY_ID"
        private const val CITY_COUNT = 10
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var cityAdapter: CityAdapter? = null

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
            } else {
            }
        }

    @Inject
    lateinit var factoryApp: AppViewModelFactory

    private val viewModel: MainViewModel by viewModels{
        factoryApp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()

        binding.svSearch.setOnQueryTextListener(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestLocationAccess()
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        loadWeatherFromNearCities()
    }


    override fun onQueryTextSubmit(p0: String?): Boolean {
        viewModel.onQuerySubmit(p0)
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun requestLocationAccess() {
        locationPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun loadWeatherFromNearCities() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var locationTask = fusedLocationProviderClient.lastLocation

            locationTask.addOnSuccessListener {
                it?.let {
                    viewModel.getNearCities(it.longitude, it.latitude, CITY_COUNT)
                } ?: run {
                    viewModel.getNearCities(0.0, 0.0, CITY_COUNT)
                }
            }
            locationTask.addOnFailureListener {
                Log.e("location exp.", it.message.toString())
            }
        } else {
            viewModel.getNearCities(0.0, 0.0, CITY_COUNT)
        }
    }

    private fun navigateToDetail(weather: Weather) {
        startActivity(Intent(
            this,
            DetailWeatherActivity::class.java
        ).apply {
            putExtra(CITY_ID, weather.id)
        })
    }

    private fun initObservers() {
        viewModel.queryWeather.observe(this) {
            it.fold(onSuccess = { queryWeather ->
                navigateToDetail(queryWeather)
            }, onFailure = {
                Snackbar.make(binding.root, "City not found", Snackbar.LENGTH_LONG).show()
            })
        }

        viewModel.nearCityWeather.observe(this) {
            it.fold(onSuccess = { citiesWeather ->
                cityAdapter = CityAdapter(citiesWeather) {
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
            }, onFailure = {
                Snackbar.make(binding.root, "Location not found", Snackbar.LENGTH_LONG).show()
            })
        }
    }
}
