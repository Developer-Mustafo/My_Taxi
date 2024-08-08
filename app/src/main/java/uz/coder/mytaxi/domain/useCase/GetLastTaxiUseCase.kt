package uz.coder.mytaxi.domain.useCase

import uz.coder.mytaxi.domain.repository.TaxiRepository

data class GetLastTaxiUseCase(val repository: TaxiRepository) {
    operator fun invoke() = repository.getLast()
}