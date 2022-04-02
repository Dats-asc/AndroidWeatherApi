package com.example.androidweatherapi

import android.app.Application
import com.example.androidweatherapi.di.AppComponent
import com.example.androidweatherapi.di.module.AppModule
import com.example.androidweatherapi.di.DaggerAppComponent
import com.example.androidweatherapi.di.module.NetModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }
}