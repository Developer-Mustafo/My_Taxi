package uz.coder.mytaxi.domain.useCase

import uz.coder.mytaxi.domain.repository.TaxiRepository
import javax.inject.Inject

class GetLastTaxiUseCase @Inject constructor(private val repository: TaxiRepository) {
    operator fun invoke() = repository.getLast()
}