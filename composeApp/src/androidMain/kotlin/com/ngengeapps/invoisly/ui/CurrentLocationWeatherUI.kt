package com.ngengeapps.invoisly.ui

import LocationServiceUtil
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import api.WeatherAPIClient
import components.HomeUI
import data.OneCallResponse
import data.Response
import kotlinx.coroutines.launch


@Composable
fun CurrentLocationWeatherUI(apiClient: WeatherAPIClient) {
    val context = LocalContext.current
    val locationUtil = LocationServiceUtil(context)

    var timeZone: String by remember {
        mutableStateOf("")
    }

    var lastSearchedPlace by remember {
        mutableStateOf("OKC")
    }
    var response: Response<OneCallResponse> by remember {
        mutableStateOf(Response.Uninitialized())
    }
    var usePrecision: Boolean by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()


    val locationsPermissionsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permsMap ->
            val fineLocationGranted = permsMap.getOrDefault(ACCESS_FINE_LOCATION, false)
            if (anyPermissionGranted(
                    context,
                    listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                )
            ) {
                response = Response.Loading()
                usePrecision = fineLocationGranted
                coroutineScope.launch {
                    val result = locationUtil.getCurrentLocation(usePrecision)
                    if (result.isSuccess) {
                        val coordinates = result.getOrNull()!!
                        response = apiClient.getWeatherResponse(
                            coordinates.latitude,
                            coordinates.longitude
                        )


                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Please grant location permissions to show weather",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }

    LaunchedEffect(Unit) {
        val coarsePermission = ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
        val finePermission = ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
        val fineLocationGranted = finePermission == PackageManager.PERMISSION_GRANTED
        // Verify that any permissions is granted
        Toast.makeText(context, "Checking permissions", Toast.LENGTH_LONG).show()
        if (anyPermissionGranted(context, listOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION))) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_LONG).show()
            response = Response.Loading()
            usePrecision = fineLocationGranted
            coroutineScope.launch {
                val result = locationUtil.getCurrentLocation(usePrecision)
                if (result.isSuccess) {
                    val coordinates = result.getOrNull()!!
                    response = apiClient.getWeatherResponse(
                        coordinates.latitude,
                        coordinates.longitude
                    )
                }
            }
        } else {
            val shouldShowRationale = (shouldShowRequestRationale(
                context,
                ACCESS_COARSE_LOCATION
            ) || shouldShowRequestRationale(
                context,
                ACCESS_FINE_LOCATION
            ))
            if (shouldShowRationale) {
                Toast.makeText(context, "Please grant location permissions", Toast.LENGTH_LONG)
                    .show()
            }
            locationsPermissionsLauncher.launch(
                arrayOf(
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
                )
            )

        }
    }


    when (response) {
        is Response.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }


        is Response.Success -> {
            timeZone = response.data!!.timezone
            HomeUI(response = response.data!!, lastSearchedPlace)
        }

        is Response.Uninitialized -> {

        }

        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text(text = "Error ${response.message}") }
        }
    }


}


fun shouldShowRequestRationale(context: Context, permission: String): Boolean {
    return if (context is ComponentActivity) {
        context.shouldShowRequestPermissionRationale(permission)
    } else false

}

fun anyPermissionGranted(context: Context, permissions: List<String>): Boolean {
    return permissions.any {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

}
