package com.lek.absoluteweather.ui.details

import androidx.lifecycle.ViewModel
import com.lek.domain.usecase.GetSelectedWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    useCase: GetSelectedWeatherUseCase
) : ViewModel() {

    val weather = useCase()
}