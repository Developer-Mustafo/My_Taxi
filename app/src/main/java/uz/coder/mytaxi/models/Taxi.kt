package uz.coder.mytaxi.models

data class Taxi(
    val latitude: Double,
    val longitude: Double,
    val altitude:Double,
    val id:Long = UNDEFINE_ID
){
    companion object{
        const val UNDEFINE_ID = 0L
    }
}
