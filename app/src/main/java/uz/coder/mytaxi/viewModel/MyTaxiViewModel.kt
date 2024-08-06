package uz.coder.mytaxi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.coder.mytaxi.models.Taxi
import uz.coder.mytaxi.repository.TaxiRepositoryImpl

class MyTaxiViewModel(application: Application):AndroidViewModel(application) {
    private val repo = TaxiRepositoryImpl(application)
    fun getTaxi() : StateFlow<Taxi>{
        val flow = MutableStateFlow(Taxi(60.6101185, 41.5755407, 70.69999694824219))
        viewModelScope.launch {
            repo.getLast().collect{
                flow.emit(it)
            }
        }
        return flow
    }
}