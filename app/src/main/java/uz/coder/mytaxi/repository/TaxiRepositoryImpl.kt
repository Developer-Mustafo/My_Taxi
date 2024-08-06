package uz.coder.mytaxi.repository

import android.app.Application
import kotlinx.coroutines.flow.channelFlow
import uz.coder.mytaxi.location.db.MyTaxiDatabase
import uz.coder.mytaxi.models.Taxi
import uz.coder.mytaxi.models.TaxiDbModel

class TaxiRepositoryImpl(application: Application):TaxiRepository {
    private val db = MyTaxiDatabase.getInstance(application).taxiDao()
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