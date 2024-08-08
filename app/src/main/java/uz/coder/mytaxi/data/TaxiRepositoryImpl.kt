package uz.coder.mytaxi.data

import android.app.Application
import kotlinx.coroutines.flow.channelFlow
import uz.coder.mytaxi.data.db.TaxiDatabase
import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.data.db.TaxiDbModel
import uz.coder.mytaxi.domain.repository.TaxiRepository

class TaxiRepositoryImpl(application: Application): TaxiRepository {
    private val db = TaxiDatabase.getInstance(application).taxiDao()
    override suspend fun addTaxi(taxi: Taxi) {
        db.insert(TaxiDbModel(taxi.latitude,taxi.longitude, taxi.altitude, taxi.id))
    }

    override fun getLast() = channelFlow {
        db.getLastLocation().collect {
            if (it.latitude!=0.0){
                send(Taxi(it.latitude,it.longitude, it.altitude, it.id))
            }
        }
    }
}