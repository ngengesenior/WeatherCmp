import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents

@OptIn(ExperimentalForeignApi::class)
@Composable
fun CurrentWeatherForLocation(service: GeocodingService = geoService(),
                              locationManager:IosLocationManager) {
    val iosGeoService = service as IosGeocodingService

    var location:Pair<Double,Double>? by remember { mutableStateOf(null) }
    var permissionStatus:LocationPermissionStatus by remember { mutableStateOf(LocationPermissionStatus.NOT_DETERMINED) }

    Column(Modifier.fillMaxSize()) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            permissionStatus = locationManager.requestLocationPermission()
            if (permissionStatus == LocationPermissionStatus.ACCEPTED) {
                val locationResult = locationManager.requestCurrentLocation()
                if (locationResult.isSuccess) {
                    println("A result was obtained")
                    locationResult.getOrNull()?.let {
                        location = Pair(it.getLongitude(),it.getLatitude())
                    }
                } else {
                    println("Error: ${locationResult.exceptionOrNull()?.message}")
                }
            }

        }
    }

    when(permissionStatus) {
        LocationPermissionStatus.NOT_DETERMINED -> {
            Text("Requesting location permission")
        }
        LocationPermissionStatus.RESTRICTED_OR_DENIED -> {
            Text("Location permissions were denied or are restricted")
        }
        LocationPermissionStatus.ACCEPTED -> {
            if (location != null) {
                Text("Latitude ${location?.first},Longitude:${location?.second}")
            }
        }
    }
}