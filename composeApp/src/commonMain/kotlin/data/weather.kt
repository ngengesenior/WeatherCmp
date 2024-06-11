package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneCallResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    val current: CurrentWeather,
    val minutely: List<MinutelyWeather>? = null,
    val hourly: List<HourlyWeather>? = null,
    val daily: List<DailyWeather>? = null,
    val alerts: List<WeatherAlert>? = null
)

@Serializable
data class CurrentWeather(
    val dt: Long,
    @SerialName("sunrise") val sunrise: Long? = null,
    @SerialName("sunset") val sunset: Long? = null,
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("wind_gust") val windGust: Double? = null,
    val weather: List<Weather>,
    val rain: Rain? = null
)

@Serializable
data class MinutelyWeather(
    val dt: Long,
    val precipitation: Double
)

@Serializable
data class HourlyWeather(
    val dt: Long,
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("wind_gust") val windGust: Double? = null,
    val weather: List<Weather>,
    val pop: Double,
    val rain: Rain? = null
)

@Serializable
data class DailyWeather(
    val dt: Long,
    @SerialName("sunrise") val sunrise: Long,
    @SerialName("sunset") val sunset: Long,
    @SerialName("moonrise") val moonrise: Long,
    @SerialName("moonset") val moonset: Long,
    @SerialName("moon_phase") val moonPhase: Double,
    val summary: String,
    val temp: Temperature,
    @SerialName("feels_like") val feelsLike: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Int,
    @SerialName("wind_gust") val windGust: Double? = null,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double,
    val rain: Double? = null,
    val uvi: Double
)

@Serializable
data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Rain(
    @SerialName("1h") val oneHour: Double? = null
)

@Serializable
data class WeatherAlert(
    val senderName: String,
    val event: String,
    val start: Long,
    val end: Long,
    val description: String,
    val tags: List<String>
)

val sampleOneCallResponse = OneCallResponse(
    lat = 37.7749,
    lon = -122.4194,
    timezone = "America/Los_Angeles",
    timezoneOffset = -25200,
    current = CurrentWeather(
        dt = 1628511123,
        sunrise = 1628493123,
        sunset = 1628543123,
        temp = 15.0,
        feelsLike = 14.5,
        pressure = 1013,
        humidity = 78,
        dewPoint = 11.0,
        uvi = 5.0,
        clouds = 20,
        visibility = 10000,
        windSpeed = 5.5,
        windDeg = 180,
        windGust = 7.5,
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "clear sky",
                icon = "01d"
            )
        ),
        rain = Rain(oneHour = 0.0)
    ),
    minutely = listOf(
        MinutelyWeather(
            dt = 1628511180,
            precipitation = 0.0
        )
    ),
    hourly = listOf(
        HourlyWeather(
            dt = 1628511600,
            temp = 15.0,
            feelsLike = 14.5,
            pressure = 1013,
            humidity = 78,
            dewPoint = 11.0,
            uvi = 5.0,
            clouds = 20,
            visibility = 10000,
            windSpeed = 5.5,
            windDeg = 180,
            windGust = 7.5,
            weather = listOf(
                Weather(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d"
                )
            ),
            pop = 0.0,
            rain = Rain(oneHour = 0.0)
        )
    ),
    daily = listOf(
        DailyWeather(
            dt = 1628496000,
            sunrise = 1628493123,
            sunset = 1628543123,
            moonrise = 1628502123,
            moonset = 1628553123,
            moonPhase = 0.5,
            summary = "Sunny",
            temp = Temperature(
                day = 15.0,
                min = 10.0,
                max = 20.0,
                night = 12.0,
                eve = 14.0,
                morn = 11.0
            ),
            feelsLike = FeelsLike(
                day = 14.5,
                night = 11.5,
                eve = 13.5,
                morn = 10.5
            ),
            pressure = 1013,
            humidity = 78,
            dewPoint = 11.0,
            windSpeed = 5.5,
            windDeg = 180,
            windGust = 7.5,
            weather = listOf(
                Weather(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d"
                )
            ),
            clouds = 20,
            pop = 0.0,
            rain = 0.0,
            uvi = 5.0
        )
    ),
    alerts = listOf(
        WeatherAlert(
            senderName = "NWS",
            event = "Heat Advisory",
            start = 1628496000,
            end = 1628539200,
            description = "Heat advisory in effect from noon to 8 PM.",
            tags = listOf("Heat")
        )
    )
)


