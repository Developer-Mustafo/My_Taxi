package uz.coder.mytaxi.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.mytaxi.models.Taxi

interface TaxiRepository {
    suspend fun addTaxi(taxi: Taxi)
    fun getLast(): Flow<Taxi>
}