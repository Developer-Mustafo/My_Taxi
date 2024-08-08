package uz.coder.mytaxi

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp
import uz.coder.mytaxi.data.service.LocationService

@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}