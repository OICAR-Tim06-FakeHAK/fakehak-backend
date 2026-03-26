package hr.algebra.fakehak.dto.cases

import jakarta.validation.constraints.NotNull

data class CaseCreateRequestDto(
    @field:NotNull(message = "User ID is required")
    val userId: Long,

    @field:NotNull(message = "Vehicle ID is required")
    val vehicleId: Long,

    @field:NotNull(message = "Latitude is required")
    val latitude: Double,

    @field:NotNull(message = "Longitude is required")
    val longitude: Double,

    val description: String? = null
)