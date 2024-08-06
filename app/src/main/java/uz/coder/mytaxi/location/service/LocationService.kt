package uz.coder.mytaxi.location.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.coder.mytaxi.R
import uz.coder.mytaxi.location.DefaultLocationClient
import uz.coder.mytaxi.location.LocationClient
import uz.coder.mytaxi.location.db.MyTaxiDatabase
import uz.coder.mytaxi.location.models.TaxiDbModel
import uz.coder.mytaxi.todo.CHANNEL_ID
import uz.coder.mytaxi.todo.ID

class LocationService: Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var client:LocationClient
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        client = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val interval = 2000L
        val taxiDao = MyTaxiDatabase.getInstance(applicationContext).taxiDao()
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
                delay(interval)
                taxiDao.insert(TaxiDbModel(latitude, longitude))
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