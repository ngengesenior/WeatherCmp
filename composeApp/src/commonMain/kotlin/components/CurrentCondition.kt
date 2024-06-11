package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.OneCallResponse

@Composable
fun CurrentCondition(
    modifier: Modifier = Modifier,
    isMyLocation: Boolean = true,
    apiResponse: OneCallResponse,
    city: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        if (isMyLocation) {
            Text(
                text = "My Location",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Text(
            city,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            "${apiResponse.current.temp}°",
            style = MaterialTheme.typography.displayLarge
        )
        Text(apiResponse.current.weather[0].main, fontWeight = FontWeight.SemiBold)
        apiResponse.daily?.let {
            Text("H:${it[0].temp.max}° L:${it[0].temp.min}°", fontWeight = FontWeight.SemiBold)
        }


    }
}