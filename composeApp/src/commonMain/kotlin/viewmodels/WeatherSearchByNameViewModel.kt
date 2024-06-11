package viewmodels

import GeocodingService
import Place
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.WeatherAPIClient
import data.OneCallResponse
import data.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherSearchByNameViewModel(
    private val apiClient: WeatherAPIClient = WeatherAPIClient(),
    private val geocodingService: GeocodingService
) : ViewModel() {

    private val _place: MutableStateFlow<Place?> = MutableStateFlow(null)
    val place: StateFlow<Place?> = _place.asStateFlow()
    private val _weatherResponse: MutableStateFlow<Response<OneCallResponse>> =
        MutableStateFlow(Response.Uninitialized())
    val weatherResponse: StateFlow<Response<OneCallResponse>> = _weatherResponse.asStateFlow()

    fun getWeatherPlaceName(placeName: String) {
        viewModelScope.launch {
            val geocodePlaceResult = geocodingService.geocodeAddress(placeName)
            if (geocodePlaceResult.isSuccess) {
                // Geocoding succeeded, proceed with weather API call
                setPlace(geocodePlaceResult.getOrNull()?.copy(name = placeName))
                _weatherResponse.value =
                    apiClient.getWeatherResponse(_place.value!!.latitude, _place.value!!.longitude)
            } else {
                // Geocoding failed, handle the error accordingly
                val error = geocodePlaceResult.exceptionOrNull()
                val message =
                    error?.message ?: "Error occurred while geocoding $placeName"
                _weatherResponse.value = Response.Error(message)
            }
        }
    }

    private fun setPlace(place: Place?) {
        _place.value = place
    }

}