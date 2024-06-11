@file:OptIn(ExperimentalMaterial3Api::class)

package components

import Place
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import api.WeatherAPIClient
import data.OneCallResponse
import data.Response
import geoService
import viewmodels.WeatherSearchByNameViewModel

@Composable
fun AppUi(
    modifier: Modifier = Modifier,
    weatherSearchVm: WeatherSearchByNameViewModel = WeatherSearchByNameViewModel(
        apiClient = WeatherAPIClient(),
        geocodingService = geoService()
    )
) {
    var placeSearchText by remember { mutableStateOf("") }
    val place: Place? by weatherSearchVm.place.collectAsState(null)

    val keyboardController = LocalSoftwareKeyboardController.current
    var timeZone: String by remember {
        mutableStateOf("")
    }
    var showSearchBar by remember {
        mutableStateOf(false)

    }
    val response: Response<OneCallResponse> by weatherSearchVm.weatherResponse.collectAsState(
        Response.Uninitialized()
    )

    Column(modifier = modifier.fillMaxSize()) {

        IconButton(onClick = {
            showSearchBar = !showSearchBar

        }, modifier = Modifier.align(Alignment.End)) {
            Icon(Icons.Rounded.Search, contentDescription = "")
        }
        if (showSearchBar) {

            BasicAlertDialog(onDismissRequest = {
                showSearchBar = false
            }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = placeSearchText,
                        onValueChange = { placeSearchText = it },
                        trailingIcon = {
                            IconButton(onClick = {

                            }) {
                                Icon(Icons.Rounded.Search, contentDescription = "")
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        singleLine = true,
                        placeholder = { Text("Name of City/Place") },
                        keyboardActions = KeyboardActions(onSearch = {
                            keyboardController?.hide()

                            if (placeSearchText.isNotEmpty()) {
                                weatherSearchVm.getWeatherPlaceName(placeSearchText)
                                placeSearchText = ""
                                showSearchBar = false
                                /*response = if (latLngResult.isSuccess) {
                                    val coordinates = latLngResult.getOrNull()
                                    if (coordinates != null) {
                                        api.getWeatherResponse(
                                            coordinates.latitude,
                                            coordinates.longitude
                                        )
                                    } else {
                                        Response.Error("Could not get valid coordinates for $text")
                                    }
                                } else {
                                    Response.Error(
                                        latLngResult.exceptionOrNull()?.message
                                            ?: "Unknown error"
                                    )
                                }*/

                            }

                        })
                    )
                }

            }
        }
        when (response) {
            is Response.Loading -> {
                Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }


            is Response.Success -> {
                timeZone = response.data!!.timezone
                println("There is a response and we got it")
                HomeUI(response = response.data!!, place?.name!!)
                /*LazyColumn {
                    items(response.data!!.daily!!.size) {
                        DailyUI(dailyWeather = response.data!!.daily!![it], timeZoneId = timeZone)
                        HorizontalDivider()
                    }
                }*/
            }

            is Response.Uninitialized -> {

            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { Text(text = "Error ${response.message}") }
            }
        }

    }


}