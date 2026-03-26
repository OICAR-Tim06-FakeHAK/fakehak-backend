package hr.algebra.fakehak.dto.vehicle

import java.time.LocalDate

data class VehicleUpdateRequestDto(
    val brand: String? = null,
    val model: String? = null,
    val registrationPlate: String? = null,
    val firstRegistrationDate: LocalDate? = null
)
