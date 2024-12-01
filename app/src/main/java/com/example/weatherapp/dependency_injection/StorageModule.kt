package com.example.weatherapp.dependency_injection

import com.example.weatherapp.storage.SharedPreferenceManager
import org.koin.dsl.module

val storageModule = module {
    single { SharedPreferenceManager(context = get(), gson = get()) }
}