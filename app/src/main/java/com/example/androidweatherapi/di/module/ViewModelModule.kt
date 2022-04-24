package com.example.androidweatherapi.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidweatherapi.di.ViewModelKey
import com.example.androidweatherapi.presentation.DetailWeatherViewModel
import com.example.androidweatherapi.presentation.MainViewModel
import com.example.androidweatherapi.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factoryApp: AppViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailWeatherViewModel::class)
    fun bindDetailWeatherViewModel(
        viewModel: DetailWeatherViewModel
    ): ViewModel
}