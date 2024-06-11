package components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.DailyWeather
import formatToDayAndMonth
import formatToDayString
import iconUrlFromCode
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import utcDateTimeToLocalDateTime


@Composable
fun DailyUI(
    modifier: Modifier = Modifier, dailyWeather: DailyWeather,
    timeZoneId: String
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(80.dp)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                utcDateTimeToLocalDateTime(dailyWeather.dt, timeZoneId).formatToDayString(),
                //"Tuesday",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                utcDateTimeToLocalDateTime(dailyWeather.dt, timeZoneId).formatToDayAndMonth(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Text(
            "${dailyWeather.temp.day}°",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Box(modifier = Modifier.size(65.dp)) {
            KamelImage(
                resource = asyncPainterResource(iconUrlFromCode(dailyWeather.weather[0].icon)),
                contentDescription = null, animationSpec = tween(),
            )
        }

    }
}

