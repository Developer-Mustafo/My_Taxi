package uz.coder.mytaxi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.coder.mytaxi.R
import uz.coder.mytaxi.models.Taxi
import uz.coder.mytaxi.repository.TaxiRepositoryImpl

class MyTaxiViewModel(private val application: Application):AndroidViewModel(application) {
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

    fun list(): List<BottomItem> {
        return listOf(BottomItem(R.drawable.switch_button, application.getString(R.string.rate), application.getString(R.string.six_eight), R.drawable.right),
            BottomItem(R.drawable.rectangle_stack, application.getString(R.string.orders), application.getString(R.string.zero), R.drawable.right),
            BottomItem(R.drawable.rocket, application.getString(R.string.bordur), "", R.drawable.right))
    }
}