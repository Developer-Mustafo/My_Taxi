package uz.coder.mytaxi.domain.useCase

import uz.coder.mytaxi.domain.model.Taxi
import uz.coder.mytaxi.domain.repository.TaxiRepository
import javax.inject.Inject

class AddTaxiUseCase @Inject constructor(private val repository: TaxiRepository) {
    suspend operator fun invoke(taxi: Taxi) = repository.addTaxi(taxi)
}