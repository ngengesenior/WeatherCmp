package api

import data.OneCallResponse
import data.Response
import data.Response.Loading
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class WeatherAPIClient {
    companion object {
        const val BASE_URL =
            "https://api.openweathermap.org/data/3.0/onecall?appid=b9964bb67d54c3dd19a1deaecc6d9171&units=metric"
    }

    private val client = HttpClient(clientEngine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

    }

    suspend fun getWeatherResponse(lat: Double, lon: Double): Response<OneCallResponse> {
        var result: Response<OneCallResponse> = Loading()
        try {
            val response = client.get("$BASE_URL&lat=$lat&lon=$lon")
            if (response.status == HttpStatusCode.OK) {
                val decodedResponse = Json.decodeFromString<OneCallResponse>(response.body())
                println(decodedResponse)
                result = Response.Success(decodedResponse)
            } else {
                result = Response.Error("Http Error: ${response.status.value}")
            }

        } catch (ex: Exception) {

            result = Response.Error("Error occurred while fetching weather data:${ex.message}")
        }

        return result
    }

}

