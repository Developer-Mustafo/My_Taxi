package uz.coder.mytaxi.data.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.coder.mytaxi.CHANNEL_ID
import uz.coder.mytaxi.ID
import uz.coder.mytaxi.R
import uz.coder.mytaxi.data.location.DefaultLocationClient
import uz.coder.mytaxi.data.location.LocationClient
import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.domain.useCase.AddTaxiUseCase
import javax.inject.Inject

@AndroidEntryPoint
class LocationService: Service() {

    @Inject
    lateinit var addTaxiUseCase: AddTaxiUseCase

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var client: LocationClient
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        client = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(application)
        )
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val interval = 1000L
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(this.getString(R.string.app_name))
            .setContentText(this.getString(R.string.gettingLocation))
            .setSmallIcon(android.R.drawable.ic_dialog_map)
        val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        client.getLocationUpdates(interval)
            .catch { e-> e.printStackTrace() }
            .onEach { location->
                val longitude = location.longitude
                val latitude = location.latitude
                val altitude = location.altitude
                delay(interval+9000L)
                Log.d("TAG", "onStartCommand: $longitude $latitude $altitude")
                addTaxiUseCase(Taxi(latitude, longitude, altitude))
                manager.notify(ID, notification.build())
            }
            .launchIn(scope)
        startForeground(ID, notification.build())
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}