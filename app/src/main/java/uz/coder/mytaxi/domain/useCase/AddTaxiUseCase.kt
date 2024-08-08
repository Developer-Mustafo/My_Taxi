package uz.coder.mytaxi.domain.useCase

import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.domain.repository.TaxiRepository

data class AddTaxiUseCase(val repository: TaxiRepository) {
    suspend operator fun invoke(taxi: Taxi) = repository.addTaxi(taxi)
}