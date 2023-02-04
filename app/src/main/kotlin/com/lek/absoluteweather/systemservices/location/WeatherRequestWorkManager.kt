package com.lek.absoluteweather.systemservices.location

import android.content.Context
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.lek.absoluteweather.systemservices.CITY_NAME_KEY
import com.lek.absoluteweather.systemservices.WeatherService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRequestWorkManager @Inject constructor(
   @ApplicationContext private val context: Context
) {

    fun publishLocation(city: String) {
        val inputData = Data.Builder().putString(CITY_NAME_KEY, city).build()
        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(WeatherService::class.java, 15L, TimeUnit.MINUTES)
                .setInitialDelay(0L, TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .build()
        WorkManager.getInstance(context).enqueue(periodicWorkRequest)
    }
}