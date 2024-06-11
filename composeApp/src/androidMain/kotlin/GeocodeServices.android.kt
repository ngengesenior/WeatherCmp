import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume


@Suppress("DEPRECATION")
class AndroidGeocodingService(context: Context) : GeocodingService {
    private val geocoder = Geocoder(context, Locale.getDefault())
    override suspend fun geocodeAddress(address: String): Result<Place> {
        return suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(address, 1) { addresses ->
                    if (addresses.isNotEmpty()) {
                        val firstAddress = addresses[0]
                        firstAddress.locality
                        continuation.resume(
                            Result.success(
                                Place(
                                    firstAddress.latitude,
                                    firstAddress.longitude,
                                    country = firstAddress.countryName,
                                    zipCode = firstAddress.postalCode,
                                    name = firstAddress.locality,
                                    locality = firstAddress.locality,
                                )
                            )
                        )
                    } else {
                        continuation.resume(Result.failure(Exception("Could not get coordinates for $address")))
                    }
                }
            } else {
                val addresses = geocoder.getFromLocationName(address, 1)
                if (!addresses.isNullOrEmpty()) {
                    val firstAddress = addresses[0]
                    continuation.resume(
                        Result.success(
                            Place(
                                firstAddress.latitude,
                                firstAddress.longitude
                            )
                        )
                    )
                } else {
                    continuation.resume(Result.failure(Exception("Could not get coordinates for $address")))
                }
            }


        }
    }
}


@Composable
actual fun geoService(): GeocodingService {
    val context = LocalContext.current
    return AndroidGeocodingService(context)
}