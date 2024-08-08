package uz.coder.mytaxi.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.mapbox.mapboxsdk.geometry.LatLng
import dagger.hilt.android.AndroidEntryPoint
import org.ramani.compose.CameraPosition
import org.ramani.compose.LocationRequestProperties
import org.ramani.compose.LocationStyling
import org.ramani.compose.MapLibre
import uz.coder.mytaxi.BuildConfig
import uz.coder.mytaxi.MAP_ID
import uz.coder.mytaxi.R
import uz.coder.mytaxi.presentation.viewModel.TaxiViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                0
            )
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
        val key = BuildConfig.MAPTILER_API_KEY
        val styleUrl = "https://api.maptiler.com/maps/$MAP_ID/style.json?key=$key"
        setContent {
            MapScreen(mapStyleUrl = styleUrl)
        }
    }

    @Composable
    fun MapScreen(modifier: Modifier = Modifier, mapStyleUrl:String) {
        val viewModel = hiltViewModel<TaxiViewModel>()
        viewModel.getTaxi()
        val modelState by viewModel.last.collectAsState()
        var checked by remember { mutableStateOf(false) }
        Box(modifier = modifier.fillMaxSize()) {
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
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Card(
                    modifier
                        .padding()
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp)), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
                ){
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Icon(Icons.Default.Menu, null, modifier.size(24.dp))
                    }
                }
                Card(
                    modifier
                        .width(192.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(14.dp))) {
                    Row(modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)) {
                        FilledIconToggleButton(checked = checked, onCheckedChange = { checked = it }, modifier = modifier
                            .width(92.dp)
                            .height(56.dp), colors = IconButtonDefaults.filledIconToggleButtonColors(checkedContainerColor = Color.Red, containerColor = Color.Transparent)) {
                            Text(text = stringResource(R.string.busy), fontSize = 20.sp)
                        }
                        FilledIconToggleButton(checked = !checked, onCheckedChange = { checked = !it }, modifier = modifier
                            .width(92.dp)
                            .height(56.dp), colors = IconButtonDefaults.filledIconToggleButtonColors(
                            checkedContainerColor = Color.Green, containerColor = Color.Transparent)) {
                            Text(text = stringResource(R.string.active), fontSize = 20.sp)
                        }
                    }
                }
                Card(
                    modifier
                        .padding()
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp)), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)){
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "95", fontSize = 20.sp)
                    }
                }
            }

        }
    }
}
