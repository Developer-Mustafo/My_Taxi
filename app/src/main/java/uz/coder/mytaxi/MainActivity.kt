package uz.coder.mytaxi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mapbox.mapboxsdk.geometry.LatLng
import org.ramani.compose.CameraPosition
import org.ramani.compose.LocationRequestProperties
import org.ramani.compose.LocationStyling
import org.ramani.compose.MapLibre

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val key = BuildConfig.MAPTILER_API_KEY

        // Find other maps in https://cloud.maptiler.com/maps/
        val mapId = "basic-v2"

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
                    latitude = 41.5562,
                    longitude = 60.6344,
                    altitude = 150.0
                ),
                zoom = 5.0
            ),
            locationRequestProperties = LocationRequestProperties(),
            locationStyling = LocationStyling(
                enablePulse = true,
            )
        )
    }
}