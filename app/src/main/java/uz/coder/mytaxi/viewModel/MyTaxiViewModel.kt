package uz.coder.mytaxi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.coder.mytaxi.location.db.MyTaxiDatabase

class MyTaxiViewModel(application: Application):AndroidViewModel(application) {
    val dao = MyTaxiDatabase.getInstance(application).taxiDao()
    fun getTaxi() = dao.getLastLocation()
}