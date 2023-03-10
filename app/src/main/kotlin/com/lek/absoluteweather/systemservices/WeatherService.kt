package com.lek.absoluteweather.systemservices

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lek.absoluteweather.R
import com.lek.absoluteweather.ui.weatherlist.MainActivity
import com.lek.domain.model.WeatherRequest
import com.lek.domain.model.WeatherResult
import com.lek.domain.usecase.FetchWeatherUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

const val CITY_NAME_KEY = "city"
private const val NOTIFICATION_ID = 300

private const val CHANNEL_ID = "absolute_weather"

@HiltWorker
class WeatherService @AssistedInject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    @Assisted val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val city = inputData.getString(CITY_NAME_KEY).orEmpty()
        return if (city.isNotBlank()) {
            when (val result = fetchWeatherUseCase(WeatherRequest(city = city))) {
                is WeatherResult.Failure -> Result.failure()
                is WeatherResult.Success -> {
                    val data = result.data
                    if (data.isNotEmpty() && isAppOnForeground(context).not()) {
                        data.firstOrNull { it.isToday }?.let { weather ->
                            val title = context.getString(
                                R.string.weather_notification_title,
                                weather.getCurrentTemp().toString(),
                                weather.city
                            )
                            val description = context.getString(
                                R.string.weather_notification_message,
                                weather.main,
                                weather.description
                            )
                            displayNotification(title, description)
                        }
                    }
                    Result.success()
                }
            }
        } else {
            Result.failure()
        }
    }

    private fun isAppOnForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = context.packageName
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && appProcess.processName == packageName
            ) {
                return true
            }
        }
        return false
    }

    private fun displayNotification(title: String, message: String) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            flag
        )

        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setVibrate(null)
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}