package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.OneCallResponse

@Composable
fun HomeUI(response: OneCallResponse, city: String) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CurrentCondition(apiResponse = response, city = city)
        }
        response.hourly?.let {
            item {
                HourlyForecastUI(conditions = it, timeZone = response.timezone)
            }
        }
        response.daily?.let {
            item {
                DailyConditionUI(weather = it, timeZoneId = response.timezone)
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .height(100.dp)
            ) {
                UVIndex(
                    response.current.uvi,
                    modifier = Modifier.weight(1f)

                )
                if (response.current.sunrise != null && response.current.sunset != null) {
                    SunriseSunsetUI(
                        sunriseTime = response.current.sunrise,
                        sunsetTime = response.current.sunset,
                        timeZoneId = response.timezone,
                        modifier = Modifier.weight(1f)

                    )
                }

            }
        }

    }
}