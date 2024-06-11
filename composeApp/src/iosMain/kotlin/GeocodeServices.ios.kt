@file:OptIn(ExperimentalForeignApi::class)

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLPlacemark
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class IosGeocodingService : GeocodingService {
    private val geocoder = CLGeocoder()
    override suspend fun geocodeAddress(address: String): Result<Place> {
        return suspendCancellableCoroutine { continuation ->
            geocoder.geocodeAddressString(address) { placemarks, error ->
                if (error != null) {
                    continuation.resumeWithException(Exception("Error geocoding address:$address,${error.localizedDescription}"))
                }
                if (!placemarks.isNullOrEmpty()) {
                    val placemark: CLPlacemark = placemarks[0] as CLPlacemark
                    val clLocationCoordinate: CLLocationCoordinate2D? =
                        placemark.location?.coordinate?.useContents { this }
                    val latitude: Double? = clLocationCoordinate?.latitude
                    val longitude: Double? = clLocationCoordinate?.longitude
                    if (latitude != null && longitude != null) {
                        continuation.resume(
                            Result.success(
                                Place(
                                    latitude = latitude,
                                    longitude = longitude,
                                    name = placemark.name,
                                    zipCode = placemark.postalCode
                                )
                            )
                        )
                    } else {
                        continuation.resumeWithException(Exception("Error geocoding address:$address"))
                    }

                } else {
                    continuation.resumeWithException(Exception("Failed  geocoding address:$address"))
                }

            }
        }
    }

}

@Composable
actual fun geoService(): GeocodingService = IosGeocodingService()