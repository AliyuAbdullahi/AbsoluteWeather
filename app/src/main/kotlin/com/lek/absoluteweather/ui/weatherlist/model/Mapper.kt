package com.lek.absoluteweather.ui.weatherlist.model

import android.content.Context
import com.lek.absoluteweather.R
import com.lek.data.repository.ModelMapper.isTomorrow
import com.lek.domain.model.Weather

fun Weather.getDayOfTheWeek(context: Context): String =
    when {
        isToday -> context.getString(R.string.today_text)
        this.isTomorrow() -> context.getString(R.string.tomorrow_text)
        else -> context.getString(R.string.formatted_day_month, this.dayOfTheWeek.name)
    }