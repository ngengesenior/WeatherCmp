package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.HourlyWeather
import formatToAmPm
import utcDateTimeToLocalDateTime

@Composable
fun HourConditionUI(hourlyWeather: HourlyWeather, timeZone: String) {
    Column(
        
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(utcDateTimeToLocalDateTime(hourlyWeather.dt, timeZone).formatToAmPm())
        SmallWeatherIcon(hourlyWeather.weather[0].icon)
        Text("${hourlyWeather.temp}°")
    }
}


@Composable
fun HourlyForecastUI(conditions: List<HourlyWeather>, timeZone: String) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = CardDefaults.elevatedShape
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Header(text = "HOURLY FORECAST", modifier = Modifier.padding(horizontal = 16.dp))
        HorizontalDivider()
        LazyRow(horizontalArrangement = Arrangement.SpaceEvenly) {
            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(conditions.size) { index ->
                HourConditionUI(conditions[index], timeZone)
            }
        }
    }

}