package api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.java.Java


actual val clientEngine: HttpClientEngine = Java.create()