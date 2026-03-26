package hr.algebra.fakehak.dto.vehicle

import java.time.LocalDate
import java.time.LocalDateTime

data class VehicleResponseDto(
    val id: Long,
    val brand: String,
    val model: String,
    val vin: String,
    val registrationPlate: String,
    val firstRegistrationDate: LocalDate,
    val createdAt: LocalDateTime
)
