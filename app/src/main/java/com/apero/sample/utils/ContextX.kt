package com.apero.sample.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService

fun Context.hasNetwork(): Boolean {
   val connectivityManager = getSystemService<ConnectivityManager>()
   return connectivityManager?.activeNetwork
       ?.let(connectivityManager::getNetworkCapabilities)
       ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}