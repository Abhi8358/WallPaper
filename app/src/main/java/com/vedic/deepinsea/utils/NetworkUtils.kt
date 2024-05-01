package com.vedic.deepinsea.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtils @Inject constructor(@ApplicationContext context: Context) {

    private var connectivityManager: ConnectivityManager?
    init {
        connectivityManager = getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
        requestNetwork()
    }

    private fun requestNetwork() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            }

        }
        connectivityManager?.requestNetwork(networkRequest, networkCallback)
    }

    fun isNetworkConnected(): Boolean {
        return connectivityManager?.activeNetwork != null
    }
}