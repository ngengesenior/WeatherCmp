package components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import formatToHourMinute
import getUVText
import utcDateTimeToLocalDateTime

@Composable
fun UVIndex(uvi: Double, modifier: Modifier = Modifier) {

    Card(modifier = modifier) {
        Header(text = "UV INDEX", modifier = modifier.padding(horizontal = 16.dp))
        Text("${uvi.toInt()}", style = MaterialTheme.typography.headlineMedium)
        HorizontalDivider()
        Text(getUVText(uvi), style = MaterialTheme.typography.titleMedium)

    }
}

@Composable
fun SunriseSunsetUI(
    sunriseTime: Long,
    sunsetTime: Long,
    timeZoneId: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Header(text = "SUNSET", modifier = modifier.padding(horizontal = 16.dp))
        Text(
            utcDateTimeToLocalDateTime(sunsetTime, timeZoneId).formatToHourMinute(),
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        Text("Sunrise:${utcDateTimeToLocalDateTime(sunriseTime, timeZoneId).formatToHourMinute()}")
    }
}