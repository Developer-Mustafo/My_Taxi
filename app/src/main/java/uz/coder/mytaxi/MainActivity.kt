package uz.coder.mytaxi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mapbox.mapboxsdk.geometry.LatLng
import org.ramani.compose.CameraPosition
import org.ramani.compose.LocationRequestProperties
import org.ramani.compose.LocationStyling
import org.ramani.compose.MapLibre
import uz.coder.mytaxi.location.service.LocationService
import uz.coder.mytaxi.viewModel.MyTaxiViewModel

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
        val key = BuildConfig.MAPTILER_API_KEY
        val mapId = "bright-v2"
        val styleUrl = "https://api.maptiler.com/maps/$mapId/style.json?key=$key"
        setContent {
            MapScreen(mapStyleUrl = styleUrl)
        }
    }

    @Composable
    fun MapScreen(modifier: Modifier = Modifier, mapStyleUrl:String) {
        val viewModel = viewModel<MyTaxiViewModel>()
        val modelState by viewModel.getTaxi().collectAsState()
        Log.d("TAG", "MapScreen: $modelState")
        MapLibre(
            modifier = modifier.fillMaxSize(),
            styleUrl = mapStyleUrl,
            cameraPosition = CameraPosition(
                target = LatLng(
                    longitude = modelState.longitude,
                    latitude = modelState.latitude,
                    altitude = modelState.altitude
                ),
                zoom = 17.04
            ),
            locationRequestProperties = LocationRequestProperties(),
            locationStyling = LocationStyling(
                enablePulse = true
            )
        )
    }
}
