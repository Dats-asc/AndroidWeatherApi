package com.example.androidweatherapi.presentation

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.androidweatherapi.domain.entity.nearcities.NearCities
import com.example.androidweatherapi.domain.entity.nearcities.NearCity
import com.example.androidweatherapi.data.api.WeatherRepositoryImpl
import com.example.androidweatherapi.data.api.WeatherResponse.WeatherResponse
import com.example.androidweatherapi.data.api.mapper.WeatherMapper
import com.example.androidweatherapi.databinding.ActivityMainBinding
import com.example.androidweatherapi.domain.entity.detail.Weather
import com.example.androidweatherapi.domain.usecase.GetWeatherByIdUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherByLocationUseCase
import com.example.androidweatherapi.domain.usecase.GetWeatherUseCase
import com.example.androidweatherapi.presentation.cities.City
import com.example.androidweatherapi.presentation.cities.CityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    companion object {
        private const val CITY_ID = "CITY_ID"
    }

    private lateinit var getWeatherUseCase: GetWeatherUseCase

    private val repository by lazy {
        WeatherRepositoryImpl(WeatherMapper())
    }

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
        initUseCases()

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
            var response: Weather? = null
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
        var response: NearCities? = null
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

    private fun navigateToDetail(weather: Weather) {
        startActivity(Intent(
            this,
            DetailWeatherActivity::class.java
        ).apply {
            putExtra(CITY_ID, weather.id)
        })
    }

    private suspend fun getCityByName(name: String): Weather? {
        var weather: Weather? = null
        lifecycleScope.launch {
            try {
                weather = getWeatherUseCase.invoke(name)
            } catch (ex: Exception) {
                Log.e("", ex.message.toString())
            }
        }.join()
        val t = 1
        return weather
    }

    private fun initUseCases() {
        getWeatherUseCase = GetWeatherUseCase(WeatherRepositoryImpl(WeatherMapper()))
    }
}
