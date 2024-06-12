import androidx.compose.ui.window.ComposeUIViewController
import ui.WeatherSearchUI

fun MainViewController() = ComposeUIViewController {
    val manager = IosLocationManager()
    CurrentWeatherForLocation(locationManager = manager)
}