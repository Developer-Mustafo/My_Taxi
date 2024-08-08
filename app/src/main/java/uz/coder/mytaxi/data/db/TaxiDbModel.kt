package uz.coder.mytaxi.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.coder.mytaxi.domain.model.Taxi.Companion.UNDEFINE_ID

@Entity("taxi")
data class TaxiDbModel(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id:Long = UNDEFINE_ID
)