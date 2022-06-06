package com.example.androidweatherapi.di.module

import com.example.androidweatherapi.presentation.DetailWeatherModule
import com.example.androidweatherapi.presentation.MainActivity
import com.example.androidweatherapi.presentation.MainModule
import com.itis.template.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindsModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailWeatherModule::class])
    fun contributeDetailWeatherActivity(): MainActivity
}
