@file:OptIn(ExperimentalForeignApi::class)

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
fun  CLLocation.getLatitude():Double = this.coordinate().useContents { latitude }
fun CLLocation.getLongitude():Double = this.coordinate().useContents { longitude }