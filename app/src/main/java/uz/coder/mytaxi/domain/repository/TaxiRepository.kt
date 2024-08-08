package uz.coder.mytaxi.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.mytaxi.domain.model.Taxi

interface TaxiRepository {
    suspend fun addTaxi(taxi: Taxi)
    fun getLast(): Flow<Taxi>
}