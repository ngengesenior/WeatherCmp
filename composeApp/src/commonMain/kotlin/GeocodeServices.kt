import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

interface GeocodingService {
    suspend fun geocodeAddress(address: String): Result<Place>
}

@Serializable
data class Place(
    val latitude: Double,
    val longitude: Double,
    val name: String? = null,
    val zipCode: String? = null,
    val locality: String? = null,
    val country: String? = null
)

@Composable
expect fun geoService(): GeocodingService




