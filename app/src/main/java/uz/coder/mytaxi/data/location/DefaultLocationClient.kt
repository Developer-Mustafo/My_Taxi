package uz.coder.mytaxi.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import uz.coder.mytaxi.hasLocationPermission

@Suppress("DEPRECATION")
data class DefaultLocationClient(
    private val context: Context,
    private val client:FusedLocationProviderClient
): LocationClient {
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long) = callbackFlow {
        if (!context.hasLocationPermission()) {
            throw LocationClient.LocationException()
        }
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!isGpsEnabled && !isNetworkEnabled) {
            throw LocationClient.LocationException()
        }
        val request = LocationRequest.create()
            .setFastestInterval(interval)
            .setInterval(interval)
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.locations.lastOrNull()?.let {location->
                   launch { send(location) }
                }
            }
        }
        client.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
        awaitClose{
            client.removeLocationUpdates(locationCallback)
        }
    }
}