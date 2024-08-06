package uz.coder.mytaxi.location.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("taxi")
data class TaxiDbModel(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0
)