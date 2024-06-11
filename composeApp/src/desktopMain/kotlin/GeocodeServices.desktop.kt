import androidx.compose.runtime.Composable
import kotlinx.coroutines.suspendCancellableCoroutine


class DesktopGeocodingService : GeocodingService {
    override suspend fun geocodeAddress(address: String): Result<Place> {
        return suspendCancellableCoroutine { contintuation ->

        }
    }
}

@Composable
actual fun geoService(): GeocodingService = DesktopGeocodingService()