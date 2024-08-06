package uz.coder.mytaxi.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import uz.coder.mytaxi.models.Taxi.Companion.UNDEFINE_ID

@Entity("taxi")
data class TaxiDbModel(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id:Long = UNDEFINE_ID
)