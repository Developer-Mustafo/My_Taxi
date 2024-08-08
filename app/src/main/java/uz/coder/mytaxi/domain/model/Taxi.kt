package uz.coder.mytaxi.domain.model

import javax.inject.Inject

data class Taxi @Inject constructor(
    val latitude: Double,
    val longitude: Double,
    val altitude:Double,
    val id:Long = UNDEFINE_ID
){
    companion object{
        const val UNDEFINE_ID = 0L
    }
}
