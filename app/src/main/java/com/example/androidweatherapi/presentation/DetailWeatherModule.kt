package com.example.androidweatherapi.presentation

import androidx.lifecycle.ViewModel
import com.example.androidweatherapi.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailWeatherModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailWeatherViewModel::class)
    fun bindDetailWeatherViewModel(
        viewModel: DetailWeatherViewModel
    ): ViewModel
}