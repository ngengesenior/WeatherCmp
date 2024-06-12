@file:OptIn(ExperimentalForeignApi::class)

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLGeocoder
import platform.CoreLocation.CLLocation
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
                    return@geocodeAddressString
                }
                if (!placemarks.isNullOrEmpty()) {
                    val placemark: CLPlacemark = placemarks[0] as CLPlacemark
                    val clLocationCoordinate: CLLocationCoordinate2D? =
                        placemark.location?.coordinate?.useContents { this }
                    val longitude: Double? = clLocationCoordinate?.longitude
                    val latitude: Double? = clLocationCoordinate?.latitude
                    println("Lon $longitude,Lat:$latitude")
                    if (latitude != null && longitude != null) {
                        continuation.resume(
                            Result.success(
                                Place(
                                    latitude = latitude,
                                    longitude = longitude,
                                    name = placemark.name,
                                    zipCode = placemark.postalCode,
                                    country = placemark.country
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

    suspend fun reverseGeocodeAddress(location:CLLocation):Result<Place> {
        return suspendCancellableCoroutine {continuation->
            geocoder.reverseGeocodeLocation(location) {placemarks,error ->
                if (error != null) {
                    continuation.resumeWithException(Exception("Error:${error.localizedDescription}"))
                }
                if (!placemarks.isNullOrEmpty()) {
                    val placeMark:CLPlacemark = placemarks[0] as CLPlacemark
                    val location2d = location.coordinate.useContents {
                        this
                    }
                    val place = Place(
                        latitude = location2d.latitude,
                        longitude = location2d.longitude,
                        name = placeMark.name,
                        country = placeMark.country,
                        locality = placeMark.locality


                    )
                    continuation.resume(Result.success(place))
                } else {
                    continuation.resumeWithException(Exception("Could not reverse goecode addpress"))
                }

            }
        }
    }

}

@Composable
actual fun geoService(): GeocodingService = IosGeocodingService()