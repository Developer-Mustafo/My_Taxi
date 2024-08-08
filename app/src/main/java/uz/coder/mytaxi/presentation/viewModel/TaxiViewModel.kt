package uz.coder.mytaxi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.domain.useCase.GetLastTaxiUseCase
import javax.inject.Inject

@HiltViewModel
class TaxiViewModel @Inject constructor(private val getLastTaxiUseCase:GetLastTaxiUseCase):ViewModel() {
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