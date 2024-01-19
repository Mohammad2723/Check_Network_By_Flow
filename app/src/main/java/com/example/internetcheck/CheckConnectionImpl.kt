package com.example.internetcheck

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class CheckConnectionImpl(context: Context) : CheckConnection {


    private val cm: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<CheckConnection.Status> {

        return callbackFlow {

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(CheckConnection.Status.Availabe)
                    }

                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(CheckConnection.Status.Losing)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {

                        send(CheckConnection.Status.Lost)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(CheckConnection.Status.Unavilable)
                    }
                }
            }
            //
            cm.registerDefaultNetworkCallback(callback)
            awaitClose {
                cm.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()

    }
}