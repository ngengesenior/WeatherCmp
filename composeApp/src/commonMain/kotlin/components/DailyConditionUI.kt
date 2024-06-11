package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.DailyWeather
import formatToDayString
import utcDateTimeToLocalDateTime

@Composable
fun OneDayConditionUI(
    dailyWeather: DailyWeather,
    timeZoneId: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(utcDateTimeToLocalDateTime(dailyWeather.dt, timeZoneId).formatToDayString())
        SmallWeatherIcon(iconCode = dailyWeather.weather[0].icon)
        Text("L: ${dailyWeather.temp.min}°")
        Text("H: ${dailyWeather.temp.min}°")

    }
}

@Composable
fun DailyConditionUI(weather: List<DailyWeather>, timeZoneId: String) {
    Card(shape = CardDefaults.elevatedShape) {
        Spacer(modifier = Modifier.height(16.dp))
        Header(
            "DAILY FORECAST",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider()
        weather.forEach {
            OneDayConditionUI(dailyWeather = it, timeZoneId = timeZoneId)
            HorizontalDivider()
        }

    }
}