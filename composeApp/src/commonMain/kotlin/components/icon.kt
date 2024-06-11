package components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import iconUrlFromCode
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun SmallWeatherIcon(iconCode: String) {
    Box(modifier = Modifier.size(65.dp)) {
        KamelImage(
            resource = asyncPainterResource(iconUrlFromCode(iconCode)),
            contentDescription = null, animationSpec = tween(),
        )
    }
}