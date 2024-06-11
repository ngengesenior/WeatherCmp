package ui

import GeocodingService
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import geoService
import kotlinx.coroutines.launch


@Composable
fun WeatherSearchUI() {
    val coroutineScope = rememberCoroutineScope()
    val service: GeocodingService = geoService()
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text, onValueChange = { text = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                coroutineScope.launch {
                    val result = service.geocodeAddress(text)
                    if (result.isSuccess) {
                        println("The coordinates are: ${result.getOrNull()}")
                    } else {
                        println("There was an error ${result.exceptionOrNull()?.message}")
                    }
                }
            })
        )
    }
}