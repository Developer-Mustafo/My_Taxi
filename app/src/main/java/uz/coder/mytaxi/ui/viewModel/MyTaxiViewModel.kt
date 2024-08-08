package uz.coder.mytaxi.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.data.TaxiRepositoryImpl
import uz.coder.mytaxi.domain.useCase.GetLastTaxiUseCase

class MyTaxiViewModel(application: Application):AndroidViewModel(application) {
    private val repo = TaxiRepositoryImpl(application)
    private val getLastTaxiUseCase = GetLastTaxiUseCase(repo)
    private val _last = MutableStateFlow(Taxi(60.6101185, 41.5755407, 70.69999694824219))
    val last = _last.asStateFlow()
    init {
        getTaxi()
    }
    fun getTaxi(){
        viewModelScope.launch {
            getLastTaxiUseCase().collect{
                _last.emit(it)
            }
        }
    }
}