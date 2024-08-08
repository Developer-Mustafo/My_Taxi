package uz.coder.mytaxi.data

import kotlinx.coroutines.flow.channelFlow
import uz.coder.mytaxi.data.db.TaxiDatabase
import uz.coder.mytaxi.data.db.TaxiDbModel
import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.domain.repository.TaxiRepository
import javax.inject.Inject

class TaxiRepositoryImpl @Inject constructor(private val db:TaxiDatabase): TaxiRepository {
    override suspend fun addTaxi(taxi: Taxi) {
        db.taxiDao().insert(TaxiDbModel(taxi.latitude,taxi.longitude, taxi.altitude, taxi.id))
    }

    override fun getLast() = channelFlow {
        db.taxiDao().getLastLocation().collect {
            if (it.latitude!=0.0){
                send(Taxi(it.latitude,it.longitude, it.altitude, it.id))
            }
        }
    }
}