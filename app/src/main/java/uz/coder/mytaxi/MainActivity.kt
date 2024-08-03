package uz.coder.mytaxi

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mapbox.mapboxsdk.geometry.LatLng
import org.ramani.compose.CameraPosition
import org.ramani.compose.LocationRequestProperties
import org.ramani.compose.LocationStyling
import org.ramani.compose.MapLibre
import org.ramani.compose.UiSettings
import uz.coder.mytaxi.location.service.LocationService
import uz.coder.mytaxi.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.POST_NOTIFICATIONS
            ),
            0
        )
        ContextCompat.startForegroundService(this, Intent(this, LocationService::class.java))
        val key = BuildConfig.MAPTILER_API_KEY
        val mapId = "bright-v2"
        val styleUrl = "https://api.maptiler.com/maps/$mapId/style.json?key=$key"
        setContent {
            MapScreen(mapStyleUrl = styleUrl)
        }
    }

    @Composable
    fun MapScreen(modifier: Modifier = Modifier, mapStyleUrl:String) {
        MapLibre(
            modifier = modifier.fillMaxSize(),
            styleUrl = mapStyleUrl,
            cameraPosition = CameraPosition(
                target = LatLng(
                    longitude = 60.6101173,
                    latitude = 41.5755424
                ),
                zoom = 17.05
            ),
            locationRequestProperties = LocationRequestProperties(),
            locationStyling = LocationStyling(
                enablePulse = true,
                accuracyColor = Color.RED,
                pulseColor = Color.YELLOW
            )
        )
    }
}